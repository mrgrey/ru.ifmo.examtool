package examtool.calculation;

import examtool.model.Mark;
import examtool.model.MarkCalculator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class BayesMarkCalculator implements MarkCalculator {

    private static final List<Pair<Integer, Integer>> marks = new ArrayList<Pair<Integer, Integer>>();

    static {
        for (int i = 20; i >= 1; i--) {
            final int rightQuestions = i * 90 / 20;
            marks.add(Pair.of(i, rightQuestions));
        }
    }

    @Override
    public Mark calculate(final List<Boolean> questionAnswerResults) {
        final int score = calcProbabilityByBayes(marks, questionAnswerResults);
        return new Mark(score);
    }

    private int calcProbabilityByBayes(final List<Pair<Integer, Integer>> marks, final Collection<Boolean> answerMask) {

        float sum = 0;
        for (final Pair<Integer, Integer> mark : marks) {
            int rightAnswers = mark.getRight();

            float res = calcProbabilityEventForHyp(answerMask, rightAnswers);

            sum += res * calcHypProbability();
        }

        float resSum = 0;
        int maxMark = 0;
        for (final Pair<Integer, Integer> mark : marks) {
            final Integer pointCnt = mark.getLeft();
            int rightAnswers = mark.getRight();

            float res = calcProbabilityEventForHyp(answerMask, rightAnswers) * calcHypProbability();
            resSum += (res / sum);
            if (resSum > 0.5 && pointCnt == 20 || maxMark == 0 && resSum > 0.6) {
                maxMark = pointCnt;
            }
        }

        return maxMark;
    }

    private float calcProbabilityEventForHyp(final Collection<Boolean> answerMask, final int rightAnswers1) {
        int cnt = 0;
        float res = 1;
        int rightAnswers = rightAnswers1;
        int badAnswers = 100 - rightAnswers;
        for (final boolean answer : answerMask) {
            if (answer) {
                res *= ((float) rightAnswers) / (100 - cnt);
                rightAnswers--;
            } else {
                res *= ((float) badAnswers) / (100 - cnt);
                badAnswers--;
            }
            cnt++;
        }
        return res;
    }

    private float calcHypProbability() {
        return 1.0f / 20;
    }
}
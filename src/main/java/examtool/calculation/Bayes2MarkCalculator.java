package examtool.calculation;

import examtool.model.ExamSettings;
import examtool.model.Mark;
import examtool.model.MarkCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga Bolshakova
 * <p/>
 * 08.01.14 17:10
 */
public class Bayes2MarkCalculator implements MarkCalculator {

    private final ExamSettings examSettings;

    public Bayes2MarkCalculator(final ExamSettings examSettings) {
        this.examSettings = examSettings;
    }

    @Override
    public Mark calculate(final List<Boolean> questionAnswerResults) {
        final int allQuestionsCnt = examSettings.getAllQuestionsCnt();
        final int maxPointCnt = examSettings.getMaxPointCnt();
        final int percentForMaxPoint = examSettings.getPercentForMaxPoint();
        final int minProbabilityForPoint = examSettings.getMinProbabilityForPoint();

        final Map<Integer, Integer> pointToFinal = getPointToFinal(allQuestionsCnt, percentForMaxPoint, maxPointCnt);

        float sum = 0;
        float[] vals = new float[allQuestionsCnt + 1];
        for (int i = 1; i <= allQuestionsCnt; i++) {
            final float p = calculateProbabilityDirect(allQuestionsCnt, i, questionAnswerResults);
            vals[i] = p;
            sum += p;
        }

        for (int i = allQuestionsCnt - 1; i > 0; i--) {
            vals[i] += vals[i + 1];
        }

        final Map<Integer, Float> pointToProbability = new HashMap<Integer, Float>();
        for (int i1 = 1; i1 < vals.length; i1++) {
            final float val = vals[i1];
            final Integer pointCnt = pointToFinal.get(i1);
            final float p = val / sum;
            final Float oldP = pointToProbability.get(pointCnt);
            if (oldP == null || oldP < p) {
                pointToProbability.put(pointCnt, p);
            }
        }

        for (int i = maxPointCnt; i >= 1; i--) {
            if (Math.round(pointToProbability.get(i) * 100) >= minProbabilityForPoint) {
                return new Mark(i);
            }
        }

        return new Mark(0);
    }

    public float calculateProbabilityDirect(final int allQuestionsCnt, final int knownAnswersPercent, final List<Boolean> answerMask) {
        int goodAnswersCnt = knownAnswersPercent;
        int badAnswersCnt = allQuestionsCnt - goodAnswersCnt;
        int allCnt = allQuestionsCnt;

        float result = 1.0f;
        for (final Boolean item : answerMask) {
            if (item) {
                result *= (float) goodAnswersCnt-- / allCnt;
            } else {
                result *= (float) badAnswersCnt-- / allCnt;
            }
            allCnt--;
        }
        return result;
    }

    private Map<Integer, Integer> getPointToFinal(final int allQuestionsCnt, final int percentForMaxPoint, final int maxPointCnt) {
        final Map<Integer, Integer> pointToFinal = new HashMap<Integer, Integer>();
        for (int i = allQuestionsCnt; i > 0; i--) {
            int pointCnt = Math.round((float) i * maxPointCnt * 100 / percentForMaxPoint / allQuestionsCnt);
            pointToFinal.put(i, Math.min(pointCnt, maxPointCnt));
        }
        return pointToFinal;
    }

}

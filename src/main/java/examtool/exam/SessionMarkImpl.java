package examtool.exam;

import examtool.model.Mark;
import examtool.model.MarkCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 12:47
 */
public class SessionMarkImpl implements SessionMark {

    private final MarkCalculator markCalculator;
    private final List<Boolean> answersMask = new ArrayList<Boolean>();

    private final int maxQuesionCnt;

    public SessionMarkImpl(final MarkCalculator markCalculator, final int maxQuesionCnt) {
        this.markCalculator = markCalculator;
        this.maxQuesionCnt = maxQuesionCnt;
    }


    @Override
    public Mark currentMark() {
        return markCalculator.calculate(answersMask);
    }

    @Override
    public Mark nextMark(final boolean isNextAnswerCorrect) {
        final List<Boolean> nextAnswers = new ArrayList<Boolean>(answersMask.size() + 1);
        nextAnswers.addAll(answersMask);
        nextAnswers.add(isNextAnswerCorrect);
        return markCalculator.calculate(nextAnswers);
    }

    private Mark lastMark(final boolean isNextAnswerCorrect) {
        final List<Boolean> nextAnswers = new ArrayList<Boolean>(maxQuesionCnt);
        nextAnswers.addAll(answersMask);
        for (int i = 0; i < maxQuesionCnt - answersMask.size(); i++) {
            nextAnswers.add(isNextAnswerCorrect);
        }
        return markCalculator.calculate(nextAnswers);
    }

    @Override
    public Mark maxPossibleMark() {
        return lastMark(true);
    }

    @Override
    public Mark minPossibleMark() {
        return lastMark(false);
    }

    void addAnswer(final boolean isAnswerCorrect) {
        answersMask.add(isAnswerCorrect);
    }

    @Override
    public List<Boolean> getAnswersMask() {
        return Collections.unmodifiableList(answersMask);
    }
}

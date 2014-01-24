package examtool.exam;

import examtool.loading.QuestionLoader;
import examtool.model.Mark;
import examtool.model.MarkCalculator;
import examtool.model.Question;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class ExamProvider {

    private final QuestionLoader questionLoader;

    private final MarkCalculator markCalculator;

    public ExamProvider(final QuestionLoader questionLoader,
                        final MarkCalculator markCalculator) {
        this.questionLoader = questionLoader;
        this.markCalculator = markCalculator;
    }

    public ExamSession newSession() {
        return new ExamSession(this);
    }

    public final static class ExamSession {

        private final MarkCalculator markCalculator;
        private final LinkedList<Question> questionQueue;

        private final List<Boolean> answersMask;

        private Question currentQuestion;

        private ExamSession(final ExamProvider examProvider) {
            this.markCalculator = examProvider.markCalculator;

            final List<Question> questions = getQuestionsForSession(examProvider);

            this.questionQueue = new LinkedList<Question>(questions);

            this.answersMask = new LinkedList<Boolean>();

            moveToNextQuestion();
        }

        private List<Question> getQuestionsForSession(ExamProvider examProvider) {
            final List<QuestionLoader.StratumEntry> questionStratums = examProvider.questionLoader.loadQuestions();
            final List<Question> questions = new ArrayList<Question>();
            for (final QuestionLoader.StratumEntry stratum : questionStratums) {
                questions.addAll(stratum.getQuestions());
            }
            Collections.shuffle(questions);
            return questions;
        }

        private void moveToNextQuestion() {
            this.currentQuestion = !questionQueue.isEmpty() ? questionQueue.pop() : null;
        }

        public Mark currentMark() {
            return markCalculator.calculate(answersMask);
        }

        public Mark nextMark(final boolean isNextAnswerCorrect) {
            final List<Boolean> nextAnswers = new ArrayList<Boolean>(answersMask.size() + 1);
            nextAnswers.addAll(answersMask);
            nextAnswers.add(isNextAnswerCorrect);
            return markCalculator.calculate(nextAnswers);
        }

        public Question currentQuestion() {
            return currentQuestion;
        }

        public void answer(final boolean isAnswerCorrect) {
            Validate.notNull(currentQuestion, "current question is null");
            answersMask.add(isAnswerCorrect);
            moveToNextQuestion();
        }
    }
}

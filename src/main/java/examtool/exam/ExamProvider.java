package examtool.exam;

import examtool.loading.QuestionLoader;
import examtool.model.ExamConstants;
import examtool.model.Mark;
import examtool.model.MarkCalculator;
import examtool.model.Question;
import org.apache.commons.lang3.Validate;

import java.util.*;

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

        private final Map<Question, Boolean> answers;

        private Question currentQuestion;

        private ExamSession(final ExamProvider examProvider) {
            this.markCalculator = examProvider.markCalculator;

            final List<Question> allAllowedQuestions = new ArrayList<Question>(examProvider.questionLoader.loadQuestions());
            Collections.shuffle(allAllowedQuestions);
            this.questionQueue = new LinkedList<Question>(allAllowedQuestions.subList(0, ExamConstants.MAX_QUESTION_QUEUE_SIZE));

            this.answers = new LinkedHashMap<Question, Boolean>(questionQueue.size());

            moveToNextQuestion();
        }

        private void moveToNextQuestion() {
            this.currentQuestion = !questionQueue.isEmpty() ? questionQueue.pop() : null;
        }

        public Mark currentMark() {
            return markCalculator.calculate(answers.values());
        }

        public Question currentQuestion() {
            return currentQuestion;
        }

        public void answer(final boolean isAnswerCorrect) {
            Validate.notNull(currentQuestion, "current question is null");
            answers.put(currentQuestion, isAnswerCorrect);
            moveToNextQuestion();
        }
    }
}

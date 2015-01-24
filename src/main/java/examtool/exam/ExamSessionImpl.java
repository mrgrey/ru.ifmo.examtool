package examtool.exam;

import examtool.model.MarkCalculator;
import examtool.model.Question;
import org.apache.commons.lang3.Validate;

import java.util.LinkedList;
import java.util.List;

/**
* Created by Olga Bolshakova (obolshakova@yandex-team.ru)
* <p/>
* 24.01.15 12:26
*/
public final class ExamSessionImpl implements ExamSession {

    private final SessionMarkImpl sessionMark;

    private final LinkedList<Question> questionQueue;
    private Question currentQuestion;

    public ExamSessionImpl(final MarkCalculator markCalculator, final List<Question> questions) {
        this.questionQueue = new LinkedList<Question>(questions);

        this.sessionMark = new SessionMarkImpl(markCalculator, questions.size());
    }

    @Override
    public void startExam() {
        currentState.startExam();
    }

    @Override
    public void finishExam() {
        currentState.finishExam();
    }

    @Override
    public void moveToNextQuestion() {
        currentState.nextQuestion();
    }

    private void nextQuestion() {
        this.currentQuestion = !questionQueue.isEmpty() ? questionQueue.pop() : null;
    }


    @Override
    public Question currentQuestion() {
        return currentQuestion;
    }

    @Override
    public void submitAnswer(final boolean isAnswerCorrect) {
        currentState.submitAnswer(isAnswerCorrect);
    }

    @Override
    public SessionMark getMark() {
        return sessionMark;
    }

    @Override
    public SessionState getState() {
        return currentState.getName();
    }

    private final State newState = new NewState(this);
    private final State questionShowedState = new QuestionShowedState(this);
    private final State answerSubmittedState = new AnswerSubmittedState(this);
    private final State finishedState = new FinishedState(this);

    private State currentState = newState;

    private void setState(final State state) {
        this.currentState = state;
    }

    private static interface State {
        void startExam();
        void submitAnswer(boolean isTrue);
        void nextQuestion();
        void finishExam();
        SessionState getName();
    }

    private static abstract class AbstractState implements State {
        protected final ExamSessionImpl session;

        private AbstractState(final ExamSessionImpl session) {
            this.session = session;
        }

        @Override
        public void startExam() {
            throw new RuntimeException("Operation 'startExam' is not supported, because session is on " + this.getName() + " state.");
        }

        @Override
        public void submitAnswer(final boolean isTrue) {
            throw new RuntimeException("Operation 'submitAnswer' is not supported, because session is on " + this.getName() + " state.");

        }

        @Override
        public void nextQuestion() {
            throw new RuntimeException("Operation 'nextQuestion' is not supported, because session is on " + this.getName() + " state.");
        }

        @Override
        public void finishExam() {
            throw new RuntimeException("Operation 'finishExam' is not supported, because session is on " + this.getName() + " state.");
        }
    }

    private static class NewState extends AbstractState {

        private NewState(final ExamSessionImpl session) {
            super(session);
        }

        @Override
        public void startExam() {
            session.nextQuestion();
            session.setState(session.questionShowedState);
        }

        @Override
        public SessionState getName() {
            return SessionState.NEW;
        }
    }

    private static class QuestionShowedState extends AbstractState {

        private QuestionShowedState(final ExamSessionImpl session) {
            super(session);
        }


        @Override
        public void submitAnswer(final boolean isTrue) {
            Validate.notNull(session.currentQuestion, "current question is null");
            session.sessionMark.addAnswer(isTrue);
            if (session.currentQuestion == null
                    || session.sessionMark.currentMark().getScore()
                           == session.sessionMark.maxPossibleMark().getScore()) {
                session.setState(session.finishedState);
            } else {
                session.setState(session.answerSubmittedState);
            }
        }

        @Override
        public SessionState getName() {
            return SessionState.QUESTION_SHOWED;
        }
    }

    private static class AnswerSubmittedState extends AbstractState {

        private AnswerSubmittedState(final ExamSessionImpl session) {
            super(session);
        }

        @Override
        public void nextQuestion() {
            session.nextQuestion();
            session.setState(session.questionShowedState);
        }

        @Override
        public void finishExam() {
            session.setState(session.finishedState);
        }

        @Override
        public SessionState getName() {
            return SessionState.QUESTION_ANSWERED;
        }
    }

    private static class FinishedState extends AbstractState {

        private FinishedState(final ExamSessionImpl session) {
            super(session);
        }

        @Override
        public SessionState getName() {
            return SessionState.FINISHED;
        }

    }
}

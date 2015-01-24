package examtool.exam;

import examtool.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 19:58
 */
public class ObservableExamSessionImpl implements ObservableExamSession {

    private final ExamSession delegate;

    private final List<ExamSessionObserver> observers = new ArrayList<ExamSessionObserver>(10);

    public ObservableExamSessionImpl(final ExamSession delegate) {
        this.delegate = delegate;
    }

    private void fireObservers() {
        for (final ExamSessionObserver observer : observers) {
            observer.sessionChanged();
        }
    }

    @Override
    public void fireSessionCreated() {
        fireObservers();
    }

    @Override
    public void registerObserver(final ExamSessionObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void startExam() {
        this.delegate.startExam();
        fireObservers();
    }

    @Override
    public void submitAnswer(final boolean isAnswerCorrect) {
        this.delegate.submitAnswer(isAnswerCorrect);
        fireObservers();
    }

    @Override
    public void moveToNextQuestion() {
        this.delegate.moveToNextQuestion();
        fireObservers();
    }

    @Override
    public void finishExam() {
        this.delegate.finishExam();
        fireObservers();
    }

    @Override
    public Question currentQuestion() {
        return this.delegate.currentQuestion();
    }

    @Override
    public SessionMark getMark() {
        return this.delegate.getMark();
    }

    @Override
    public SessionState getState() {
        return this.delegate.getState();
    }
}

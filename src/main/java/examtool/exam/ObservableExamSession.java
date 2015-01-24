package examtool.exam;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 19:56
 */
public interface ObservableExamSession extends ExamSession {

    void registerObserver(ExamSessionObserver observer);

    void fireSessionCreated();

}

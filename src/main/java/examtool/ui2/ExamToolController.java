package examtool.ui2;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 20:03
 */
public interface ExamToolController {

    void newStudent();

    void startExam();

    void submitAnswer(boolean isAnswerCorrect);

    void moveToNextQuestion();

    void finishExam();

    void advancedMode(boolean isOn);

}

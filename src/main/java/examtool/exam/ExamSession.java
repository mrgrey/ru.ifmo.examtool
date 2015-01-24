package examtool.exam;

import examtool.model.Question;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 12:35
 */
public interface ExamSession {

    void startExam();

    void submitAnswer(boolean isAnswerCorrect);

    void moveToNextQuestion();

    void finishExam();


    Question currentQuestion();

    SessionMark getMark();

    SessionState getState();

}

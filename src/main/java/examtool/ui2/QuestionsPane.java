package examtool.ui2;

import examtool.exam.ExamSessionObserver;
import examtool.exam.ObservableExamSession;
import examtool.exam.SessionState;
import examtool.model.ExamSettings;
import examtool.model.Question;

import javax.swing.*;

import static examtool.ui.HtmlRenderUtil.bold;
import static examtool.ui.HtmlRenderUtil.content;
import static examtool.ui.HtmlRenderUtil.line;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 13:22
 */
public class QuestionsPane extends JTextPane implements ExamSessionObserver {

    private ObservableExamSession examSession;

    private final ExamSettings examSettings;

    public QuestionsPane(final ExamSettings examSettings) {
        super();

        setContentType("text/html");
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setDragEnabled(false);
        setEditable(false);

        this.examSettings = examSettings;
    }

    public void setModel(final ObservableExamSession examSession) {
        this.examSession = examSession;
        examSession.registerObserver(this);
    }

    @Override
    public void sessionChanged() {
        final SessionState state = examSession.getState();
        if (state == SessionState.NEW) {
            renderWelcomeScreen();
        } else if (state == SessionState.QUESTION_SHOWED) {
            renderNextQuestion();
        } else if (state == SessionState.QUESTION_ANSWERED) {
            renderIntermediateScreen();
        } else if (state == SessionState.FINISHED) {
            renderFinishScreen();
        }
    }

    private void renderWelcomeScreen() {
        this.setText(content(
                line("Добрый день! Я — приниматор экзамена, и я рад приветствовать вас."),
                line(),
                line("К сегодняшнему дню я подготовил ", bold("" + examSettings.getAllQuestionsCnt()),
                        " различных вопросов, и уже выбрал для вас ", bold("" +
                        examSettings.getQuestionsSampleSize()), " из них."),
                line(),
                line("Максимальный балл, который можно получить — ", bold("" + examSettings.getMaxPointCnt()), "."),
                line(),
                line("Желаю удачи!")
        ));

    }

    private void renderNextQuestion() {
        final Question question = examSession.currentQuestion();
        if (question != null) {
            this.setText(content(question.getText()));
        }
    }

    private void renderIntermediateScreen() {
        this.setText(content(
                line("Забираем текущий балл (",
                        bold("" + examSession.getMark().currentMark().getScore()),
                        ") или отвечаем на следующий вопрос?"))
        );
    }

    private void renderFinishScreen() {
        this.setText(content(line("Поздравляю! Экзамен завершён."),
                line(),
                line("Вы набрали ", bold("" + examSession.getMark().currentMark().getScore()),
                        " баллов из ", bold("" + examSettings.getMaxPointCnt()),
                        ". Учитывая ваши ответы, больше набрать уже нельзя =)")));
    }
}

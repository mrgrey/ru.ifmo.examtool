package examtool.ui2;

import examtool.exam.ExamSessionObserver;
import examtool.exam.ObservableExamSession;
import examtool.exam.SessionState;

import javax.swing.*;

import static examtool.ui.HtmlRenderUtil.bold;
import static examtool.ui.HtmlRenderUtil.content;
import static examtool.ui.HtmlRenderUtil.line;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 22:34
 */
public class AnswersPane extends JTextPane implements ExamSessionObserver {

    private ObservableExamSession examSession;

    public AnswersPane() {
        super();

        setContentType("text/html");
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setDragEnabled(false);
        setEditable(false);
    }

    public void setModel(final ObservableExamSession examSession) {
        this.examSession = examSession;
        examSession.registerObserver(this);
    }

    @Override
    public void sessionChanged() {
        final SessionState state = examSession.getState();
        if (state == SessionState.QUESTION_SHOWED) {
            this.setText(content(
                    line(bold("ОТВЕТ<br/>")),
                    line(examSession.currentQuestion().getAnswer())
            ));
        } else {
            setText("");
        }
    }
}

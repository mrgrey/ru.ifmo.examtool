package examtool.ui2;

import examtool.exam.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 17.01.15 22:37
 */
public class ProgressPane extends JPanel implements ExamSessionObserver {

    private ObservableExamSession examSession;

    private final JComponent[] answers;

    public ProgressPane(final int size) {
        this.answers = new JComponent[size];

        setLayout(new GridLayout(1, size));

        for (int i = 1; i <= size; i++) {
            final JComponent button = createButton();
            add(button);
            answers[i - 1] = button;
        }
    }

    @Override
    public void sessionChanged() {
        final SessionState state = examSession.getState();
        if (state == SessionState.NEW) {
            restart();
        } else if (state == SessionState.QUESTION_ANSWERED
                || state == SessionState.FINISHED) {
            updateAnswers();
        }
    }

    private void updateAnswers() {
        int i = 0;
        for (Boolean answer : examSession.getMark().getAnswersMask()) {
            final JComponent button = answers[i++];
            button.setEnabled(true);
            button.setBackground(answer ? Color.GREEN : Color.RED);
        }
    }

    private void restart() {
        for (JComponent answer : answers) {
            answer.setBackground(Color.GRAY);
        }
        repaint();
    }


    private JComponent createButton() {
        final JPanel button = new JPanel();
        button.setBackground(Color.GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    public void setModel(final ObservableExamSession examSession) {
        this.examSession = examSession;
        examSession.registerObserver(this);
    }

}

package examtool.ui2;

import examtool.exam.ExamProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 17.01.15 22:37
 */
public class ProgressPane extends JPanel {

    private ExamProvider.ExamSession examSession;

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

    public void updateAnswers() {
        int i = 0;
        for (Boolean answer : examSession.getAnswersMask()) {
            final JComponent button = answers[i++];
            button.setEnabled(true);
            button.setBackground(answer ? Color.GREEN : Color.RED);
        }
    }

    private void restart() {
        for (JComponent answer : answers) {
            answer.setBackground(Color.GRAY);
        }
    }


    private JComponent createButton() {
        final JPanel button = new JPanel();
        button.setBackground(Color.GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    public void setExamSession(final ExamProvider.ExamSession examSession) {
        this.examSession = examSession;
        restart();
    }

}

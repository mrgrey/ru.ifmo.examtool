package ru.ifmo.examtool.ui;

import ru.ifmo.examtool.exam.ExamProvider;
import ru.ifmo.examtool.loading.QuestionLoader;
import ru.ifmo.examtool.model.MarkCalculator;
import ru.ifmo.examtool.model.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class ExamForm extends JDialog {

    private static final String TITLE = "Приниматор экзамена / отправлятор в армию, версия 1.0";

    private final ExamProvider examProvider;

    private ExamProvider.ExamSession examSession;

    private JPanel contentPane;
    private JButton incorrectButton;
    private JButton correctButton;
    private JButton nextStudentButton;
    private JTextPane questionPane;
    private JLabel markLabel;

    public ExamForm(final QuestionLoader questionLoader, final MarkCalculator markCalculator) {
        setTitle(TITLE);
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.questionPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        this.examProvider = new ExamProvider(questionLoader, markCalculator);

        this.incorrectButton.addActionListener(answerListener(false));
        this.correctButton.addActionListener(answerListener(true));
        this.nextStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startNewSession();
            }
        });

        startNewSession();
    }

    private void startNewSession() {
        this.incorrectButton.setEnabled(true);
        this.correctButton.setEnabled(true);
        this.examSession = examProvider.newSession();
        refresh();
    }

    private void refresh() {
        final Question question = examSession.currentQuestion();
        if (question != null) {
            this.questionPane.setText(question.getText());
        } else {
            this.questionPane.setText("===Вопросы закончились===");
            this.incorrectButton.setEnabled(false);
            this.correctButton.setEnabled(false);
        }
        this.markLabel.setText(Integer.toString(examSession.currentMark().getScore()));
    }

    private ActionListener answerListener(final boolean isAnswerCorrect) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                examSession.answer(isAnswerCorrect);
                refresh();
            }
        };
    }

}

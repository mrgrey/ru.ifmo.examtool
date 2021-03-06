package examtool.ui;

import examtool.exam.ExamProvider;
import examtool.exam.ExamSession;
import examtool.loading.QuestionLoader;
import examtool.model.MarkCalculator;
import examtool.model.Question;
import examtool.exam.SessionMark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static examtool.ui.HtmlRenderUtil.*;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class ExamForm extends JDialog {

    private static final String TITLE = "Приниматор экзамена";

    private final ExamProvider examProvider;

    private ExamSession examSession;

    private JPanel contentPane;

    private JButton incorrectButton;
    private JButton correctButton;
    private JButton nextQuestionButton;

    private JButton nextStudentButton;
    private JTextPane questionPane;
    private JLabel currentMarkLabel;

    public ExamForm(final QuestionLoader questionLoader, final MarkCalculator markCalculator) {
        setTitle(TITLE);
        setContentPane(contentPane);
        setPreferredSize(new Dimension(800, 600));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.questionPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        this.questionPane.setDragEnabled(false);

        this.examProvider = new ExamProvider(questionLoader, markCalculator);

        this.incorrectButton.addActionListener(answerListener(false));
        this.correctButton.addActionListener(answerListener(true));
        this.nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                examSession.moveToNextQuestion();
                renderNextQuestion();
            }
        });
        this.nextStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startNewSession();
            }
        });

        startNewSession();
    }

    private void setButtonsVisibility(final boolean showCorrectWrongButtons) {
        this.incorrectButton.setVisible(showCorrectWrongButtons);
        this.correctButton.setVisible(showCorrectWrongButtons);
        this.nextQuestionButton.setVisible(!showCorrectWrongButtons);
    }

    private void startNewSession() {
        this.incorrectButton.setEnabled(true);
        this.correctButton.setEnabled(true);
        this.examSession = examProvider.newSession();
        examSession.moveToNextQuestion();
        renderNextQuestion();
    }

    private void renderNextQuestion() {
        setButtonsVisibility(true);
        final Question question = examSession.currentQuestion();
        if (question != null) {
            this.questionPane.setText(content(question.getText()));
        } else {
            this.questionPane.setText(content(bold("===Вопросы закончились===")));
            this.incorrectButton.setEnabled(false);
            this.correctButton.setEnabled(false);
        }
    }

    private void renderIntermediateScreen() {
        setButtonsVisibility(false);
        final SessionMark sessionMark = examSession.getMark();
        this.questionPane.setText(content(
                line("Пройдено вопросов: ",
                        bold(Integer.toString(sessionMark.getAnswersMask().size()) + getAnswersMask(examSession))
                ),
                line("На данный момент число баллов составляет: ",
                        bold(Integer.toString(sessionMark.currentMark().getScore()))
                ),
                line("При ", bold("правильном"), " ответе на следующий вопрос число баллов составит: ",
                        bold(Integer.toString(sessionMark.nextMark(true).getScore()))
                ),
                line("При ", bold("неправильном"), " ответе на следующий вопрос число баллов составит: ",
                        bold(Integer.toString(sessionMark.nextMark(false).getScore()))
                )
        ));
    }

    private String getAnswersMask(final ExamSession session) {
        final StringBuilder sb = new StringBuilder();
        final SessionMark sessionMark = session.getMark();
        sb.append(" ( ");
        for (Boolean answer : sessionMark.getAnswersMask()) {
            sb.append(answer ? "+ " : "- ");
        }
        sb.append(")");
        return sb.toString();
    }

    private ActionListener answerListener(final boolean isAnswerCorrect) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                examSession.submitAnswer(isAnswerCorrect);

                final SessionMark sessionMark = examSession.getMark();

                final int currentScore = sessionMark.currentMark().getScore();
                currentMarkLabel.setText(Integer.toString(currentScore));

                renderIntermediateScreen();
            }
        };
    }

}

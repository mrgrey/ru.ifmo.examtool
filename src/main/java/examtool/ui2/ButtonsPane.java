package examtool.ui2;

import examtool.exam.ExamSessionObserver;
import examtool.exam.ObservableExamSession;
import examtool.exam.SessionState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 21:35
 */
public class ButtonsPane extends JPanel implements ExamSessionObserver {

    private final JButton startExamButton = createButton("Начать!", 300);
    private final JButton nextQuestionButton = createButton("Ещё!", 300);
    private final JButton incorrectButton = createButton("Неверно", 150);
    private final JButton correctButton = createButton("Верно", 150);
    private final JButton finishButton = createButton("Забрать баллы", 200);
    private final JButton nextStudentButton = createButton("Новый студент", 200);
    private final JButton advancedModeButton = createButton(">>", 50);

    private final ScorePane scorePane;

    private final ExamToolController controller;

    private ObservableExamSession examSession;

    private boolean isAdvancedMode = false;

    public ButtonsPane(final ExamToolController controller) {
        super();

        this.controller = controller;

        nextQuestionButton.setVisible(false);
        finishButton.setVisible(false);
        startExamButton.setVisible(false);

        this.scorePane = new ScorePane();
        this.scorePane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(incorrectButton);
        add(correctButton);
        add(nextQuestionButton);
        add(startExamButton);
        add(Box.createHorizontalGlue());
        add(scorePane);
        add(Box.createHorizontalGlue());
        add(finishButton);
        add(nextStudentButton);
        add(advancedModeButton);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        incorrectButton.addActionListener(answerListener(false));
        correctButton.addActionListener(answerListener(true));
        nextStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.newStudent();
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveToNextQuestion();
            }
        });

        startExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startExam();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.finishExam();
            }
        });

        advancedModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdvancedMode) {
                    controller.advancedMode(true);
                    isAdvancedMode = true;
                    advancedModeButton.setText("<<");
                } else {
                    controller.advancedMode(false);
                    isAdvancedMode = false;
                    advancedModeButton.setText(">>");
                }

            }
        });
    }

    public void setModel(final ObservableExamSession examSession) {
        this.examSession = examSession;
        examSession.registerObserver(this);

        this.scorePane.setModel(examSession);
    }

    private ActionListener answerListener(final boolean isAnswerCorrect) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.submitAnswer(isAnswerCorrect);
            }
        };
    }

    private JButton createButton(final String text, final int width) {
        final JButton button = new JButton(text);
        final Font font = button.getFont();
        button.setFont(new Font(font.getName(), font.getStyle(), 20));
        button.setPreferredSize(new Dimension(width, 100));
        return button;
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

    private void renderFinishScreen() {
        nextStudentButton.setVisible(true);
        finishButton.setVisible(false);

        nextQuestionButton.setEnabled(false);

        incorrectButton.setEnabled(false);
        correctButton.setEnabled(false);
    }

    private void renderNextQuestion() {
        nextStudentButton.setVisible(false);
        finishButton.setVisible(true);
        finishButton.setEnabled(false);

        nextQuestionButton.setEnabled(true);

        nextQuestionButton.setVisible(false);
        startExamButton.setVisible(false);

        incorrectButton.setVisible(true);
        correctButton.setVisible(true);
    }

    private void renderIntermediateScreen() {
        nextStudentButton.setVisible(false);
        finishButton.setVisible(true);
        finishButton.setEnabled(true);

        startExamButton.setVisible(false);
        nextQuestionButton.setVisible(true);
        incorrectButton.setVisible(false);
        correctButton.setVisible(false);
    }

    private void renderWelcomeScreen() {
        nextStudentButton.setVisible(true);
        finishButton.setVisible(false);

        startExamButton.setVisible(true);
        nextQuestionButton.setVisible(false);
        incorrectButton.setVisible(false);
        correctButton.setVisible(false);

        incorrectButton.setEnabled(true);
        correctButton.setEnabled(true);
    }
}

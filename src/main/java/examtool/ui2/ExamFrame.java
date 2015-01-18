package examtool.ui2;

import examtool.exam.ExamProvider;
import examtool.loading.QuestionLoader;
import examtool.model.ExamSettings;
import examtool.model.MarkCalculator;
import examtool.model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static examtool.ui.HtmlRenderUtil.bold;
import static examtool.ui.HtmlRenderUtil.content;
import static examtool.ui.HtmlRenderUtil.line;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 17.01.15 21:36
 */
public class ExamFrame extends JFrame {

    private final ExamProvider examProvider;
    private final ExamSettings examSettings;

    private ExamProvider.ExamSession examSession;

    private final ProgressPane progressPane;
    private final JTextPane questionsPane;
    private final JPanel buttonsPane;
    private final ScorePane scorePane;

    private final JButton nextQuestionButton = createButton("Ещё!");
    private final JButton incorrectButton = createButton("Неверно");
    private final JButton correctButton = createButton("Верно");
    private final JButton nextStudentButton = createButton("Заново");

    public ExamFrame(final QuestionLoader questionLoader, final MarkCalculator markCalculator,
                     final ExamSettings examSettings) {
        super("Приниматор экзамена");

        this.examProvider = new ExamProvider(questionLoader, markCalculator);
        this.examSettings = examSettings;

        getContentPane().setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.questionsPane = new JTextPane();
        questionsPane.setContentType("text/html");
        questionsPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        questionsPane.setDragEnabled(false);
        questionsPane.setEditable(false);


        nextQuestionButton.setVisible(false);

        this.scorePane = new ScorePane();

        this.buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
        buttonsPane.add(incorrectButton);
        buttonsPane.add(correctButton);
        buttonsPane.add(nextQuestionButton);
        buttonsPane.add(Box.createHorizontalGlue());
        buttonsPane.add(scorePane);
        buttonsPane.add(Box.createHorizontalGlue());
        buttonsPane.add(nextStudentButton);
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));


        this.progressPane = new ProgressPane(examSettings.getQuestionsSampleSize());
        progressPane.setPreferredSize(buttonsPane.getSize());

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(progressPane, BorderLayout.PAGE_START);
        progressPane.setPreferredSize(new Dimension(800, 50));

        final JScrollPane scrollPane = new JScrollPane(questionsPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(buttonsPane, BorderLayout.PAGE_END);

        nextQuestionButton.setPreferredSize(new Dimension(300, 100));

        incorrectButton.addActionListener(answerListener(false));
        correctButton.addActionListener(answerListener(true));
        nextStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startNewSession();
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderNextQuestion();
            }
        });

        startNewSession();
    }

    private JButton createButton(final String text) {
        final JButton button = new JButton(text);
        final Font font = button.getFont();
        button.setFont(new Font(font.getName(), font.getStyle(), 20));
        button.setPreferredSize(new Dimension(150, 100));
        return button;
    }

    private ActionListener answerListener(final boolean isAnswerCorrect) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                examSession.answer(isAnswerCorrect);

                progressPane.updateAnswers();
                scorePane.updateValues();

                if (examSession.isFinished() || examSession.isFinishedAheadOfTime()) {
                    questionsPane.setText(content(line("Поздравляю! Экзамен завершён."),
                            line(),
                            line("Вы набрали ", bold("" + examSession.currentMark().getScore()),
                                    " баллов из ", bold("" + examSettings.getMaxPointCnt()),
                                    ". Учитывая ваши ответы, больше набрать уже нельзя =)")));
                    incorrectButton.setEnabled(false);
                    correctButton.setEnabled(false);
                } else {
                    renderIntermediateScreen();
                }
            }
        };
    }

    private void startNewSession() {
        incorrectButton.setEnabled(true);
        correctButton.setEnabled(true);

        this.examSession = examProvider.newSession();

        this.scorePane.setExamSession(this.examSession);

        this.progressPane.setExamSession(this.examSession);

        renderWelcomeScreen();
    }

    private void renderNextQuestion() {
        nextQuestionButton.setVisible(false);
        incorrectButton.setVisible(true);
        correctButton.setVisible(true);
        final Question question = examSession.currentQuestion();
        if (question != null) {
            questionsPane.setText(content(question.getText()));
        }
    }

    private void renderIntermediateScreen() {
        nextQuestionButton.setVisible(true);
        incorrectButton.setVisible(false);
        correctButton.setVisible(false);

        questionsPane.setText(content(
                line("Забираем текущий балл (",
                        bold("" + examSession.currentMark().getScore()),
                        ") или отвечаем на следующий вопрос?"))
        );
    }

    private void renderWelcomeScreen() {
        nextQuestionButton.setVisible(true);
        incorrectButton.setVisible(false);
        correctButton.setVisible(false);

        questionsPane.setText(content(
                line("Добрый день! Я — приниматор экзамена, и я рад приветствовать вас."),
                line(),
                line("К сегодня я подготовил ", bold("" + examSettings.getAllQuestionsCnt()),
                        " различных вопросов, и уже выбрал для вас ", bold("" +
                        examSettings.getQuestionsSampleSize()), " из них."),
                line(),
                line("Максимальный балл, который можно получить — ", bold("" + examSettings.getMaxPointCnt()), "."),
                line(),
                line("Желаю удачи!")
        ));
    }

}

package examtool.ui2;

import examtool.exam.*;
import examtool.model.ExamSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 17.01.15 21:36
 */
public class ExamFrame extends JFrame {

    private ObservableExamSession examSession;

    private final JPanel mainArea;
    private final ProgressPane progressPane;
    private final QuestionsPane questionsPane;

    private final AnswersPane answersPane;
    private final ButtonsPane buttonsPane;

    public ExamFrame(final ExamToolController controller,
                     final ExamSettings examSettings) {
        super("Приниматор экзамена");

        getContentPane().setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.questionsPane = new QuestionsPane(examSettings);
        this.answersPane = new AnswersPane();

        this.buttonsPane = new ButtonsPane(controller);

        this.progressPane = new ProgressPane(examSettings.getQuestionsSampleSize());

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(progressPane, BorderLayout.PAGE_START);
        progressPane.setPreferredSize(new Dimension(800, 50));

        this.mainArea = new JPanel();
        setShowAnswer(false);

        getContentPane().add(mainArea, BorderLayout.CENTER);

        answersPane.setVisible(false);

        getContentPane().add(buttonsPane, BorderLayout.PAGE_END);

    }

    public void setModel(final ObservableExamSession session) {
        this.examSession = session;

        this.progressPane.setModel(examSession);
        this.questionsPane.setModel(examSession);
        this.answersPane.setModel(examSession);
        this.buttonsPane.setModel(examSession);
    }

    public void setShowAnswer(final boolean isTrue) {
        answersPane.setVisible(isTrue);

        mainArea.removeAll();
        final JScrollPane scrollPane = new JScrollPane(questionsPane);

        if (isTrue) {
            mainArea.setLayout(new GridLayout(2, 1));
            mainArea.add(scrollPane);

            final JScrollPane scrollPane2 = new JScrollPane(answersPane);
            mainArea.add(scrollPane2);
        } else {
            mainArea.setLayout(new GridLayout(1, 1));
            mainArea.add(scrollPane);
        }
    }
}

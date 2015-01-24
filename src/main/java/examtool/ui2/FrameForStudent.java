package examtool.ui2;

import examtool.exam.ExamSessionObserver;
import examtool.exam.ObservableExamSession;
import examtool.model.ExamSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 21:55
 */
public class FrameForStudent extends JFrame {

    private ObservableExamSession examSession;

    private final ProgressPane progressPane;
    private final QuestionsPane questionsPane;

    private final ScorePane scorePane;

    public FrameForStudent(final ExamSettings examSettings) throws HeadlessException {
        super("Приниматор экзамена");

        getContentPane().setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.questionsPane = new QuestionsPane(examSettings);

        this.progressPane = new ProgressPane(examSettings.getQuestionsSampleSize());

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(progressPane, BorderLayout.PAGE_START);
        progressPane.setPreferredSize(new Dimension(800, 50));

        final JScrollPane scrollPane = new JScrollPane(questionsPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.scorePane = new ScorePane();
        final JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer.add(scorePane);
        getContentPane().add(footer, BorderLayout.PAGE_END);
    }

    public void setModel(final ObservableExamSession session) {
        this.examSession = session;

        this.progressPane.setModel(examSession);
        this.questionsPane.setModel(examSession);
        this.scorePane.setModel(examSession);
    }
}

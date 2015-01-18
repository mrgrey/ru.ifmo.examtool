package examtool.ui2;

import examtool.exam.ExamProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 18.01.15 0:51
 */
public class ScorePane extends JPanel {

    private ExamProvider.ExamSession examSession;

    private final JLabel minScore;
    private final JLabel maxScore;
    private final JLabel nextScore;
    private final JLabel prevScore;
    private final JLabel curScore;

    public void setExamSession(final ExamProvider.ExamSession examSession) {
        this.examSession = examSession;
        restart();
    }

    public ScorePane() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.minScore = new JLabel("0");
        this.maxScore = new JLabel("0");
        this.nextScore = new JLabel("0");
        this.prevScore = new JLabel("0");
        this.curScore = new JLabel("0");

        final Font font = curScore.getFont();
        curScore.setFont(new Font(font.getName(), Font.BOLD, 50));
        nextScore.setFont(new Font(font.getName(), font.getStyle(), 30));
        prevScore.setFont(new Font(font.getName(), font.getStyle(), 30));
        minScore.setFont(new Font(font.getName(), font.getStyle(), 14));
        maxScore.setFont(new Font(font.getName(), font.getStyle(), 14));

        add(minScore);
        add(Box.createHorizontalStrut(12));
        add(prevScore);
        add(Box.createHorizontalStrut(12));
        add(curScore);
        add(Box.createHorizontalStrut(12));
        add(nextScore);
        add(Box.createHorizontalStrut(12));
        add(maxScore);
    }

    public void updateValues() {
        curScore.setText("" + examSession.currentMark().getScore());
        nextScore.setText("" + examSession.nextMark(true).getScore());
        prevScore.setText("" + examSession.nextMark(false).getScore());
        minScore.setText("" + examSession.maxMark(false).getScore());
        maxScore.setText("" + examSession.maxMark(true).getScore());
    }

    private void restart() {
        this.maxScore.setText("0");
        this.minScore.setText("0");
        this.prevScore.setText("0");
        this.nextScore.setText("0");
        this.curScore.setText("0");
    }
}

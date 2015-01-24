package examtool.ui2;

import examtool.exam.ExamProvider;
import examtool.exam.ObservableExamSession;
import examtool.model.ExamSettings;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 20:05
 */
public class ExamToolControllerImpl implements ExamToolController {

    private final ExamProvider examProvider;
    private ObservableExamSession currentSession;

    private final ExamFrame view;
    private final FrameForStudent frameForStudent;

    public ExamToolControllerImpl(final ExamProvider examProvider, final ExamSettings examSettings) {
        this.examProvider = examProvider;

        this.view = new ExamFrame(this, examSettings);
        this.view.pack();
        this.view.setVisible(true);

        this.frameForStudent = new FrameForStudent(examSettings);
        this.frameForStudent.setVisible(false);
    }

    @Override
    public void newStudent() {
        this.currentSession = examProvider.newSession();
        this.view.setModel(currentSession);
        this.frameForStudent.setModel(currentSession);

        this.currentSession.fireSessionCreated();
    }

    @Override
    public void startExam() {
        currentSession.startExam();
    }

    @Override
    public void submitAnswer(final boolean isAnswerCorrect) {
        currentSession.submitAnswer(isAnswerCorrect);
    }

    @Override
    public void moveToNextQuestion() {
        currentSession.moveToNextQuestion();
    }

    @Override
    public void finishExam() {
        currentSession.finishExam();
    }

    @Override
    public void advancedMode(final boolean isOn) {
        if (isOn) {
            final int x = view.getX();
            final int y = view.getY();

            frameForStudent.setBounds(x + 100, y + 100, 800, 600);
            frameForStudent.pack();
            frameForStudent.setVisible(true);

            view.setShowAnswer(true);
        } else {
            frameForStudent.setVisible(false);
            view.setShowAnswer(false);
        }
    }
}

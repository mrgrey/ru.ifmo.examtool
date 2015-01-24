package examtool.exam;

import examtool.loading.QuestionLoader;
import examtool.model.MarkCalculator;
import examtool.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class ExamProvider {

    private final QuestionLoader questionLoader;
    private final MarkCalculator markCalculator;

    public ExamProvider(final QuestionLoader questionLoader,
                        final MarkCalculator markCalculator) {
        this.questionLoader = questionLoader;
        this.markCalculator = markCalculator;
    }

    public ObservableExamSession newSession() {
        return new ObservableExamSessionImpl(
                new ExamSessionImpl(markCalculator, getQuestionsForSession()));
    }

    private List<Question> getQuestionsForSession() {
        final List<QuestionLoader.StratumEntry> questionStratums = questionLoader.loadQuestions();
        final List<Question> questions = new ArrayList<Question>();
        for (final QuestionLoader.StratumEntry stratum : questionStratums) {
            questions.addAll(stratum.getQuestions());
        }
        Collections.shuffle(questions);
        return questions;
    }

}

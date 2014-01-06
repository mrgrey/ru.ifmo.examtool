package examtool.loading;

import examtool.model.Question;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Yury Chuyko mrgrey@yandex-team.ru
 * Date: 06.01.14
 */
public class LatexQuestionLoader implements QuestionLoader {

    private final QuestionLoader delegate;

    public LatexQuestionLoader(final QuestionLoader delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Question> loadQuestions() {
        final List<Question> rawQuestions = delegate.loadQuestions();
        final List<Question> texedQuestions = new ArrayList<Question>(rawQuestions.size());
        for (final Question rawQuestion : rawQuestions) {
            new TeXFormula()
        }
    }

}

package examtool.loading;

import examtool.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* Author: Yury Chuyko
* Date: 24.01.14
*/
public final class Stratum {
    private final List<Question> questions;

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public Stratum(final List<Question> questions) {
        this.questions = new ArrayList<Question>(questions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stratum stratum = (Stratum) o;

        return questions.equals(stratum.getQuestions());
    }

    @Override
    public int hashCode() {
        return questions.hashCode();
    }
}

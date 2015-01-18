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

    public Stratum(final List<Question> questions) {
        this.questions = new ArrayList<Question>(questions);
    }

    public List<Question> takeRandom(final int count) {
        if (questions.size() < count) {
            throw new IllegalArgumentException("Requested too much question from stratum");
        }

        Collections.shuffle(questions);
        return questions.subList(0, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stratum stratum = (Stratum) o;

        return questions.equals(stratum.questions);

    }

    @Override
    public int hashCode() {
        return questions.hashCode();
    }
}

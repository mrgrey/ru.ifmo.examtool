package examtool.loading;

import examtool.model.Question;

import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public interface QuestionLoader {

    List<StratumEntry> loadQuestions();

    public final class StratumEntry {
        private final Stratum stratum;
        private final int shouldTakeQuestionsCount;

        public Stratum getStratum() {
            return stratum;
        }

        public int getShouldTakeQuestionsCount() {
            return shouldTakeQuestionsCount;
        }

        public List<Question> getQuestions() {
            return stratum.takeRandom(getShouldTakeQuestionsCount());
        }

        public StratumEntry(final Stratum stratum, final int shouldTakeQuestionsCount) {
            this.stratum = stratum;
            this.shouldTakeQuestionsCount = shouldTakeQuestionsCount;
        }
    }

}

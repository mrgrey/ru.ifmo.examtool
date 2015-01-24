package examtool.loading;

import examtool.model.Question;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public class RawQuestionTextBuilder implements QuestionTextBuilder {
    @Override
    public Question buildQuestion(final String rawQuestionText, final String rawAnswerText) {
        return new Question(rawQuestionText, rawAnswerText);
    }
}

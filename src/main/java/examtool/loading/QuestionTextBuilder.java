package examtool.loading;

import examtool.model.Question;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public interface QuestionTextBuilder {

    Question buildQuestion(String rawQuestionText, String rawAnswerText);

}

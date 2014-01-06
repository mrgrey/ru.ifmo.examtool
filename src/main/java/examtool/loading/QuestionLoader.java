package examtool.loading;

import examtool.model.Question;

import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public interface QuestionLoader {

    List<Question> loadQuestions();

}

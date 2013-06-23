package ru.ifmo.examtool.loading;

import ru.ifmo.examtool.model.Question;

import java.io.IOException;
import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public interface QuestionLoader {

    List<Question> loadQuestions() throws IOException;

}

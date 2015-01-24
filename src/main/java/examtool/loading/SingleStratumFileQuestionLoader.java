package examtool.loading;

import examtool.model.Question;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public class SingleStratumFileQuestionLoader implements QuestionLoader {

    private final String questionsFilePath;

    private final QuestionTextBuilder questionTextBuilder;

    private final int stratumQuestionLimit;

    private final Map<String, String> questionNumberToAnswer;

    public SingleStratumFileQuestionLoader(final String questionsFilePath,
                                           final QuestionTextBuilder questionTextBuilder,
                                           final Map<String, String> questionNumberToAnswer) {
        this(questionsFilePath, questionTextBuilder, questionNumberToAnswer, -1);
    }

    public SingleStratumFileQuestionLoader(final String questionsFilePath,
                                           final QuestionTextBuilder questionTextBuilder,
                                           final Map<String, String> questionNumberToAnswer,
                                           final int stratumQuestionLimit) {
        this.questionsFilePath = questionsFilePath;
        this.questionTextBuilder = questionTextBuilder;
        this.questionNumberToAnswer = questionNumberToAnswer;
        this.stratumQuestionLimit = stratumQuestionLimit;
    }

    @Override
    public List<StratumEntry> loadQuestions() {
        try {
            return Collections.singletonList(readQuestions(questionsFilePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private StratumEntry readQuestions(final String questionsFilePath) throws FileNotFoundException {
        final NumberedTextLoader textLoader = new SimpleNumberedTextLoader(questionsFilePath);
        final List<NumberedTextLoader.NumberedText> numberedItems = textLoader.loadText();

        final List<Question> loadedQuestions = new ArrayList<Question>();

        for (NumberedTextLoader.NumberedText numberedItem : numberedItems) {
            loadedQuestions.add(buildQuestion(numberedItem));
        }

        return new StratumEntry(
                new Stratum(loadedQuestions),
                stratumQuestionLimit
        );
    }

    protected Question buildQuestion(final NumberedTextLoader.NumberedText item) {
        final String number = item.number;
        final String answer = questionNumberToAnswer.get(number);

        return questionTextBuilder.buildQuestion(item.text, answer == null ? "???" : answer);
    }
}

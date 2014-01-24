package examtool.loading;

import examtool.model.Question;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public class SingleStratumFileQuestionLoader implements QuestionLoader {

    private final String questionsFilePath;

    private final QuestionTextBuilder questionTextBuilder;

    private final int stratumQuestionLimit;

    public SingleStratumFileQuestionLoader(final String questionsFilePath,
                                           final QuestionTextBuilder questionTextBuilder) {
        this(questionsFilePath, questionTextBuilder, -1);
    }

    public SingleStratumFileQuestionLoader(final String questionsFilePath,
                                           final QuestionTextBuilder questionTextBuilder,
                                           final int stratumQuestionLimit) {
        this.questionsFilePath = questionsFilePath;
        this.questionTextBuilder = questionTextBuilder;
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
        final File questionsFile = getFile(questionsFilePath);
        final List<Question> loadedQuestions = new ArrayList<Question>();
        final StringBuilder questionTextBuilder = new StringBuilder(1000);
        final Scanner scanner = new Scanner(questionsFile, "UTF-8");
        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            if (line.isEmpty()) {
                addQuestion(loadedQuestions, questionTextBuilder);
            } else {
                questionTextBuilder.append(line).append('\n');
            }
        }
        addQuestion(loadedQuestions, questionTextBuilder);
        scanner.close();
        return new StratumEntry(
                new Stratum(loadedQuestions),
                stratumQuestionLimit > 0 ? stratumQuestionLimit : loadedQuestions.size()
        );
    }

    private void addQuestion(final List<Question> loadedQuestions, final StringBuilder questionTextBuilder) {
        if (questionTextBuilder.length() > 0) {
            final Question question = buildQuestion(questionTextBuilder.toString());
            loadedQuestions.add(question);
            questionTextBuilder.setLength(0);
        }
    }

    protected Question buildQuestion(final String questionText) {
        return questionTextBuilder.buildQuestion(questionText);
    }

    private static File getFile(final String questionsFilePath) {
        final File questionsFile = new File(questionsFilePath);
        Validate.isTrue(questionsFile.canRead(), "can not read file " + questionsFile);
        return questionsFile;
    }
}

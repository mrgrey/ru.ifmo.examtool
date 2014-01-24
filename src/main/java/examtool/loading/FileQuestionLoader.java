package examtool.loading;

import examtool.model.Question;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static examtool.model.ExamConstants.MAX_QUESTION_QUEUE_SIZE;

/**
 * Author: Yury Chuyko
 * Date: 23.06.13
 */
public class FileQuestionLoader implements QuestionLoader {

    private String questionsFilePath;

    public FileQuestionLoader(final String questionsFilePath) {
        this.questionsFilePath = questionsFilePath;
    }

    @Override
    public List<Question> loadQuestions() {
        try {
            return Collections.unmodifiableList(readQuestions(questionsFilePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Question> readQuestions(final String questionsFilePath) throws FileNotFoundException {
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
        Validate.isTrue(loadedQuestions.size() >= MAX_QUESTION_QUEUE_SIZE, "not enough questions");
        return loadedQuestions;
    }

    private void addQuestion(final List<Question> loadedQuestions, final StringBuilder questionTextBuilder) {
        if (questionTextBuilder.length() > 0) {
            final Question question = buildQuestion(questionTextBuilder.toString());
            loadedQuestions.add(question);
            questionTextBuilder.setLength(0);
        }
    }

    protected Question buildQuestion(final String questionText) {
        return new Question(questionText);
    }

    private static File getFile(final String questionsFilePath) {
        final File questionsFile = new File(questionsFilePath);
        Validate.isTrue(questionsFile.canRead(), "can not read file " + questionsFile);
        return questionsFile;
    }
}

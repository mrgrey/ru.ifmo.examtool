package ru.ifmo.examtool.loading;

import org.apache.commons.lang3.Validate;
import ru.ifmo.examtool.model.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static ru.ifmo.examtool.model.ExamConstants.MAX_QUESTION_QUEUE_SIZE;

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

    private static List<Question> readQuestions(final String questionsFilePath) throws FileNotFoundException {
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
        Validate.isTrue(loadedQuestions.size() > MAX_QUESTION_QUEUE_SIZE, "not enough questions");
        scanner.close();
        return loadedQuestions;
    }

    private static void addQuestion(final List<Question> loadedQuestions, final StringBuilder questionTextBuilder) {
        if (questionTextBuilder.length() > 0) {
            final Question question = new Question(questionTextBuilder.toString());
            loadedQuestions.add(question);
            questionTextBuilder.setLength(0);
        }
    }

    private static File getFile(final String questionsFilePath) {
        final File questionsFile = new File(questionsFilePath);
        Validate.isTrue(questionsFile.canRead(), "can not read file " + questionsFile);
        return questionsFile;
    }
}

package ru.ifmo.examtool.exam;

import org.apache.commons.lang3.Validate;
import ru.ifmo.examtool.loading.QuestionLoader;
import ru.ifmo.examtool.model.Mark;
import ru.ifmo.examtool.model.MarkCalculator;
import ru.ifmo.examtool.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.ifmo.examtool.model.ExamConstants.MAX_QUESTION_QUEUE_SIZE;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class ExamProvider {

    private final QuestionLoader questionLoader;

    private final MarkCalculator markCalculator;

    public ExamProvider(final QuestionLoader questionLoader,
                        final MarkCalculator markCalculator) {
        this.questionLoader = questionLoader;
        this.markCalculator = markCalculator;
    }

    public ExamSession newSession() {
        return new ExamSession(this);
    }

    public final static class ExamSession {

        private final MarkCalculator markCalculator;
        private final LinkedList<Question> questionQueue;

        private final Map<Question, Boolean> answers;

        private Question currentQuestion;

        private ExamSession(final ExamProvider examProvider) {
            this.markCalculator = examProvider.markCalculator;

            final List<Question> allAllowedQuestions = new ArrayList<>(examProvider.questionLoader.loadQuestions());
            Collections.shuffle(allAllowedQuestions);
            this.questionQueue = new LinkedList<>(allAllowedQuestions.subList(0, MAX_QUESTION_QUEUE_SIZE));

            this.answers = new LinkedHashMap<>(questionQueue.size());

            moveToNextQuestion();
        }

        private void moveToNextQuestion() {
            this.currentQuestion = !questionQueue.isEmpty() ? questionQueue.pop() : null;
        }

        public Mark currentMark() {
            return markCalculator.calculate(answers.values());
        }

        public Question currentQuestion() {
            return currentQuestion;
        }

        public void answer(final boolean isAnswerCorrect) {
            Validate.notNull(currentQuestion, "current question is null");
            answers.put(currentQuestion, isAnswerCorrect);
            moveToNextQuestion();
        }
    }
}

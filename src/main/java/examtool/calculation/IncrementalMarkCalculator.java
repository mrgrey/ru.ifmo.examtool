package examtool.calculation;

import examtool.model.Mark;
import examtool.model.MarkCalculator;

import java.util.Collection;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class IncrementalMarkCalculator implements MarkCalculator {
    @Override
    public Mark calculate(final Collection<Boolean> questionAnswerResults) {
        int score = 0;
        for (final Boolean answer : questionAnswerResults) {
            if (answer) {
                score++;
            }
        }
        return new Mark(score);
    }
}

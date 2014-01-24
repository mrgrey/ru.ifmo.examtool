package examtool.calculation;

import examtool.model.Mark;
import examtool.model.MarkCalculator;

import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class DeferredMarkCalculator implements MarkCalculator {

    private final int startCalculationAfter;

    private final MarkCalculator delegate;

    public DeferredMarkCalculator(final int startCalculationAfter, final MarkCalculator delegate) {
        this.startCalculationAfter = startCalculationAfter;
        this.delegate = delegate;
    }

    @Override
    public Mark calculate(final List<Boolean> questionAnswerResults) {
        if (questionAnswerResults.size() < startCalculationAfter) {
            return new Mark(0);
        } else {
            return delegate.calculate(questionAnswerResults);
        }
    }
}

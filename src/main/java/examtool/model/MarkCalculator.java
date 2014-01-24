package examtool.model;

import java.util.List;

/**
 * Author: Yury Chuyko
 * Date: 16.06.13
 */
public interface MarkCalculator {

    Mark calculate(List<Boolean> questionAnswerResults);

}

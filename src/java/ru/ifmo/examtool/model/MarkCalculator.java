package ru.ifmo.examtool.model;

import java.util.Collection;

/**
 * Author: Yury Chuyko
 * Date: 16.06.13
 */
public interface MarkCalculator {

    Mark calculate(Collection<Boolean> questionAnswerResults);

    enum Mark {
        UNDEFINED, _2, _3, _4, _5
    }

}

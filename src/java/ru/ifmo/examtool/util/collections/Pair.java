package ru.ifmo.examtool.util.collections;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public final class Pair<L,R> {

    public final L first;

    public final R second;

    public L getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    public Pair(final L first, final R second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

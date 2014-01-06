package examtool.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
* Author: Yury Chuyko
* Date: 23.06.13
*/
public final class Question {
    private final String text;

    public String getText() {
        return text;
    }

    public Question(final String text) {
        this.text = text;
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

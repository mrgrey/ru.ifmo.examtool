package examtool.model;

/**
* Author: Yury Chuyko
* Date: 23.06.13
*/
public final class Question {
    private final String text;
    private final String answer;

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public Question(final String text, final String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (text != null ? !text.equals(question.text) : question.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}

package examtool.loading;

import java.util.List;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 23:26
 */
public interface NumberedTextLoader {

    List<NumberedText> loadText();

    public static class NumberedText {
        public final String number;
        public final String text;

        public NumberedText(final String number, final String text) {
            this.number = number;
            this.text = text;
        }
    }
}

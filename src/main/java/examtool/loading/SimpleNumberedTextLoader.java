package examtool.loading;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 23:27
 */
public class SimpleNumberedTextLoader implements NumberedTextLoader {

    private final String filePath;

    public SimpleNumberedTextLoader(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<NumberedText> loadText() {
        final File questionsFile = getFile(filePath);
        final List<NumberedText> loadedItems = new ArrayList<NumberedText>();

        final StringBuilder sb = new StringBuilder(1000);
        final Scanner scanner;
        try {
            scanner = new Scanner(questionsFile, "UTF-8");
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                if (line.isEmpty()) {
                    addItem(loadedItems, sb);
                } else {
                    sb.append(line).append('\n');
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
        addItem(loadedItems, sb);

        return loadedItems;
    }

    private void addItem(final List<NumberedText> loadedItems, final StringBuilder sb) {
        if (sb.length() > 0) {
            final String questionText = sb.toString();

            final String number = questionText.substring(0, questionText.indexOf(' '));
            final String question = questionText.substring(questionText.indexOf(' ') + 1);

            loadedItems.add(new NumberedText(number, question));
            sb.setLength(0);
        }
    }

    private static File getFile(final String questionsFilePath) {
        final File questionsFile = new File(questionsFilePath);
        Validate.isTrue(questionsFile.canRead(), "can not read file " + questionsFile);
        return questionsFile;
    }
}

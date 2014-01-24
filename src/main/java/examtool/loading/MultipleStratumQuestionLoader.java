package examtool.loading;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public class MultipleStratumQuestionLoader implements QuestionLoader {

    private final String configFilePath;

    private final QuestionTextBuilder questionTextBuilder;

    public MultipleStratumQuestionLoader(final String configFilePath,
                                         final QuestionTextBuilder questionTextBuilder) {
        this.configFilePath = configFilePath;
        this.questionTextBuilder = questionTextBuilder;
    }

    @Override
    public List<StratumEntry> loadQuestions() {
        final File configFile = new File(configFilePath);
        Validate.isTrue(configFile.canRead(), "can not read config file: " + configFilePath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(configFile, "UTF-8");

            final List<StratumEntry> out = new ArrayList<StratumEntry>();

            while (scanner.hasNextLine()) {
                final String configLine = scanner.nextLine();
                final String[] parts = configLine.split("\\t");
                Validate.isTrue(parts.length == 2, "invalid config line: %s", Arrays.toString(parts));

                final String questionsFilePath =
                        new File(configFile.getAbsoluteFile().getParent(), parts[0]).getAbsolutePath();

                final int shouldTakeFromStratum = Integer.parseInt(parts[1]);

                final SingleStratumFileQuestionLoader loader =
                        new SingleStratumFileQuestionLoader(questionsFilePath, questionTextBuilder, shouldTakeFromStratum);

                out.add(loader.loadQuestions().get(0));
            }

            scanner.close();
            return out;

        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

}

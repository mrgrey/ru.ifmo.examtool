package examtool.loading;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public class MultipleStratumQuestionLoader implements QuestionLoader {

    private final String configFilePath;
    private final String answersFilePath;

    private final QuestionTextBuilder questionTextBuilder;

    public MultipleStratumQuestionLoader(final String configFilePath,
                                         final String answersFilePath,
                                         final QuestionTextBuilder questionTextBuilder) {
        this.configFilePath = configFilePath;
        this.answersFilePath = answersFilePath;
        this.questionTextBuilder = questionTextBuilder;
    }

    private Map<String, String> loadAnswers() {
        final NumberedTextLoader textLoader = new SimpleNumberedTextLoader(answersFilePath);
        final List<NumberedTextLoader.NumberedText> numberedItems = textLoader.loadText();

        final Map<String, String> answers = new HashMap<String, String>();
        for (NumberedTextLoader.NumberedText numberedItem : numberedItems) {
            answers.put(numberedItem.number, numberedItem.text);
        }

        return answers;
    }

    @Override
    public List<StratumEntry> loadQuestions() {
        final File configFile = new File(configFilePath);
        Validate.isTrue(configFile.canRead(), "can not read config file: " + configFilePath);

        final Map<String, String> questionNumberToAnswer = loadAnswers();

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
                        new SingleStratumFileQuestionLoader(questionsFilePath, questionTextBuilder, questionNumberToAnswer, shouldTakeFromStratum);

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

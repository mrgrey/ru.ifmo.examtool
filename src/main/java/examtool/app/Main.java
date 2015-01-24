package examtool.app;

import examtool.calculation.Bayes2MarkCalculator;
import examtool.calculation.DeferredMarkCalculator;
import examtool.loading.ExamSettingsProvider;
import examtool.loading.FromFilesExamSettingsProvider;
import examtool.loading.MultipleStratumQuestionLoader;
import examtool.loading.QuestionWithImageTextBuilder;
import examtool.model.ExamSettings;
import examtool.ui.ExamForm;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class Main {

    public static void main(String[] args) {
        checkStartupParams(args);
        final String configFilePath = args[0];
        final String anwsersFilePath = args[1];
        final String settingsFilePath = args[2];

        final ExamSettingsProvider settingsProvider = new FromFilesExamSettingsProvider(
                configFilePath, settingsFilePath);

        final ExamSettings settings = settingsProvider.getSettings();

        final ExamForm form = new ExamForm(
                new MultipleStratumQuestionLoader(configFilePath, anwsersFilePath,
                        new QuestionWithImageTextBuilder(configFilePath)
                ),
                new DeferredMarkCalculator(5, new Bayes2MarkCalculator(settings))
        );
        form.pack();
        form.setVisible(true);
        System.exit(0);
    }

    private static void checkStartupParams(final String[] args) {
        if (args.length != 3) {
            System.out.println("Should run with questions path as command line argument");
            System.exit(1);
        }
    }

}

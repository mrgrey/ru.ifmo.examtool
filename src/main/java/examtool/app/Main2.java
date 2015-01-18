package examtool.app;

import examtool.calculation.Bayes2MarkCalculator;
import examtool.calculation.DeferredMarkCalculator;
import examtool.loading.ExamSettingsProvider;
import examtool.loading.FromFilesExamSettingsProvider;
import examtool.loading.MultipleStratumQuestionLoader;
import examtool.loading.QuestionWithImageTextBuilder;
import examtool.model.ExamSettings;
import examtool.ui2.ExamFrame;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 17.01.15 21:57
 */
public class Main2 {

    public static void main(String[] args) {
        checkStartupParams(args);
        final String configFilePath = args[0];
        final String settingsFilePath = args[1];

        final ExamSettingsProvider settingsProvider = new FromFilesExamSettingsProvider(
                configFilePath, settingsFilePath);

        final ExamSettings settings = settingsProvider.getSettings();

        final ExamFrame form = new ExamFrame(
                new MultipleStratumQuestionLoader(configFilePath,
                        new QuestionWithImageTextBuilder(configFilePath)
                ),
                new DeferredMarkCalculator(settings.getMinQuestionsCnt(), new Bayes2MarkCalculator(settings)),
                settings
        );
        form.pack();
        form.setVisible(true);
    }

    private static void checkStartupParams(final String[] args) {
        if (args.length != 2) {
            System.out.println("Should run with questions path as command line argument");
            System.exit(1);
        }
    }
}

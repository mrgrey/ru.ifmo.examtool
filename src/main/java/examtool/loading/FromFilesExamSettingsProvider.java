package examtool.loading;

import examtool.model.ExamSettings;
import examtool.model.ExamSettingsImpl;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 18.01.15 3:47
 */
public class FromFilesExamSettingsProvider implements ExamSettingsProvider {

    private final String configFileName;
    private final String examSettingsFileName;

    public FromFilesExamSettingsProvider(final String configFileName, final String examSettingsFileName) {
        this.configFileName = configFileName;
        this.examSettingsFileName = examSettingsFileName;
    }

    @Override
    public ExamSettings getSettings() {

        final TempExamSettings tempExamSettings = new TempExamSettings();

        try {
            fillQuestionsCntAndSampleSize(tempExamSettings);
            fillSettingsFromFile(tempExamSettings);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ExamSettingsImpl(tempExamSettings.allQuestionsCnt,
                tempExamSettings.questionsSampleSize, tempExamSettings.minQuestionsCnt,
                tempExamSettings.maxPointCnt, tempExamSettings.percentForMaxPoint, tempExamSettings.minProbabilityForPoint);
    }

    private void fillSettingsFromFile(final TempExamSettings tempExamSettings) throws FileNotFoundException {
        final Map<String, Integer> allSettings = new HashMap<String, Integer>();
        final Scanner scanner = new Scanner(new File(examSettingsFileName), "UTF-8");
        while(scanner.hasNext()) {
            final String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            final String[] splittedLine = line.split("=");

            Validate.isTrue(splittedLine.length == 2, "invalid config line: %s", Arrays.toString(splittedLine));

            allSettings.put(splittedLine[0], Integer.parseInt(splittedLine[1]));
        }

        tempExamSettings.minQuestionsCnt = allSettings.get("minQuestionsCnt");
        tempExamSettings.maxPointCnt = allSettings.get("maxPointCnt");
        tempExamSettings.percentForMaxPoint = allSettings.get("percentForMaxPoint");
        tempExamSettings.minProbabilityForPoint = allSettings.get("minProbabilityForPoint");
    }

    private void fillQuestionsCntAndSampleSize(final TempExamSettings tempExamSettings) throws FileNotFoundException {
        final File configFile = new File(configFileName);
        final Scanner scanner = new Scanner(configFile, "UTF-8");
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            final String[] splittedLine = line.split("\\t");

            Validate.isTrue(splittedLine.length == 2, "invalid config line: %s", Arrays.toString(splittedLine));

            final int shouldTakeFromStratum = Integer.parseInt(splittedLine[1]);

            tempExamSettings.questionsSampleSize += shouldTakeFromStratum;

            final String questionsFilePath =
                    new File(configFile.getAbsoluteFile().getParent(), splittedLine[0]).getAbsolutePath();

            final Scanner innerScanner = new Scanner(new File(questionsFilePath), "UTF-8");
            while (innerScanner.hasNext()) {
                final String line1 = innerScanner.nextLine();
                if (line1.isEmpty()) {
                    tempExamSettings.allQuestionsCnt++;
                }
            }
            tempExamSettings.allQuestionsCnt++;
            innerScanner.close();
        }
        scanner.close();
    }

    private static class TempExamSettings {
        private int allQuestionsCnt;
        private int questionsSampleSize;
        private int minQuestionsCnt;
        private int maxPointCnt;
        private int percentForMaxPoint;
        private int minProbabilityForPoint;
    }
}

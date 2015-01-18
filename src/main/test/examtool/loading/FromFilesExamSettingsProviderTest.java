package examtool.loading;

import junit.framework.TestCase;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 18.01.15 4:06
 */
public class FromFilesExamSettingsProviderTest extends TestCase {

    public void testLoadSettings() throws Exception {
        final FromFilesExamSettingsProvider provider = new FromFilesExamSettingsProvider(
                "/Users/obolshakova/Yandex.Disk.localized/Documents/itmo/Introduction to Programming (2014)/exam/config.txt",
                "/Users/obolshakova/Yandex.Disk.localized/Documents/itmo/Introduction to Programming (2014)/exam/exam_settings.txt");

        System.out.println(provider.getSettings());

    }
}

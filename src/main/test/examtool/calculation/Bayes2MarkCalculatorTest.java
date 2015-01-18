package examtool.calculation;

import examtool.model.ExamSettings;
import examtool.model.ExamSettingsImpl;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 19.01.15 0:29
 */
public class Bayes2MarkCalculatorTest extends TestCase {

    public void testPrintPoint() throws Exception {
        final ExamSettings settings = new ExamSettingsImpl(
                157, 20, 5, 10, 90, 85
        );
        final Bayes2MarkCalculator calculator = new Bayes2MarkCalculator(settings);
        System.out.println(calculator.calculate(Arrays.asList(true, true)).getScore());

    }
}

package examtool.app;

import examtool.calculation.BayesMarkCalculator;
import examtool.calculation.DeferredMarkCalculator;
import examtool.loading.FileQuestionLoader;
import examtool.ui.ExamForm;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public class Main {

    public static void main(String[] args) {
        checkStartupParams(args);
        final ExamForm form = new ExamForm(
                new FileQuestionLoader(args[0]),
                new DeferredMarkCalculator(3, new BayesMarkCalculator())
        );
        form.pack();
        form.setVisible(true);
        System.exit(0);
    }

    private static void checkStartupParams(final String[] args) {
        if (args.length != 1) {
            System.out.println("Should run with questions path as command line argument");
            System.exit(1);
        }
    }

}
package examtool.model;

/**
 * Author: Yury Chuyko
 * Date: 24.06.13
 */
public interface ExamSettings {

    int getAllQuestionsCnt();

    int getQuestionsSampleSize();

    int getMinQuestionsCnt();

    int getMaxPointCnt();

    int getPercentForMaxPoint();

    int getMinProbabilityForPoint();

}

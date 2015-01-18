package examtool.model;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 18.01.15 3:35
 */
public class ExamSettingsImpl implements ExamSettings {

    private final int allQuestionsCnt;
    private final int questionsSampleSize;
    private final int minQuestionsCnt;
    private final int maxPointCnt;
    private final int percentForMaxPoint;
    private final int minProbabilityForPoint;

    public ExamSettingsImpl(final int allQuestionsCnt, final int questionsSampleSize,
                            final int minQuestionsCnt,
                            final int maxPointCnt, final int percentForMaxPoint,
                            final int minProbabilityForPoint) {
        this.allQuestionsCnt = allQuestionsCnt;
        this.questionsSampleSize = questionsSampleSize;
        this.minQuestionsCnt = minQuestionsCnt;
        this.maxPointCnt = maxPointCnt;
        this.percentForMaxPoint = percentForMaxPoint;
        this.minProbabilityForPoint = minProbabilityForPoint;
    }

    @Override
    public int getAllQuestionsCnt() {
        return allQuestionsCnt;
    }

    @Override
    public int getQuestionsSampleSize() {
        return questionsSampleSize;
    }

    @Override
    public int getMinQuestionsCnt() {
        return minQuestionsCnt;
    }

    @Override
    public int getMaxPointCnt() {
        return maxPointCnt;
    }

    @Override
    public int getPercentForMaxPoint() {
        return percentForMaxPoint;
    }

    @Override
    public int getMinProbabilityForPoint() {
        return minProbabilityForPoint;
    }

    @Override
    public String toString() {
        return "ExamSettingsImpl{" +
                "allQuestionsCnt=" + allQuestionsCnt +
                ", questionsSampleSize=" + questionsSampleSize +
                ", minQuestionsCnt=" + minQuestionsCnt +
                ", maxPointCnt=" + maxPointCnt +
                ", percentForMaxPoint=" + percentForMaxPoint +
                ", minProbabilityForPoint=" + minProbabilityForPoint +
                '}';
    }
}

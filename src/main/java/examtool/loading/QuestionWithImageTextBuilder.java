package examtool.loading;

import examtool.model.Question;
import examtool.ui.HtmlRenderUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public class QuestionWithImageTextBuilder implements QuestionTextBuilder {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("\\[img (.*?)\\]");

    private final String imageDirName;

    private final String questionDirBaseName;

    public QuestionWithImageTextBuilder(final String questionsFilePath) {
        this(questionsFilePath, "exam_images");
    }

    public QuestionWithImageTextBuilder(final String questionsFilePath,
                                        final String imageDirName) {
        this.questionDirBaseName = new File(questionsFilePath).getAbsoluteFile().getParent();
        this.imageDirName = imageDirName;
    }

    @Override
    public Question buildQuestion(final String rawQuestionText, final String rawAnswerText) {
        return new Question(convertText(rawQuestionText), convertText(rawAnswerText));
    }

    private String convertText(final String source) {
        final Matcher imageMatcher = IMAGE_PATTERN.matcher(source);
        if (imageMatcher.find()) {
            final String imageTagReplacement = HtmlRenderUtil.image(questionDirBaseName + "/" + imageDirName + "/$1");
            return imageMatcher.replaceAll(imageTagReplacement);
        } else {
            return source;
        }
    }
}

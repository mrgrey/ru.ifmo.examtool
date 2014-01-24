package examtool.loading;

import examtool.model.Question;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Yury Chuyko
 * Date: 24.01.14
 */
public class FileQuestionWithImagesLoader extends FileQuestionLoader {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("\\[img (.*?)\\]");

    private static final String IMAGE_DIR_NAME = "exam_images";

    private final String questionDirBaseName;

    public FileQuestionWithImagesLoader(final String questionsFilePath) {
        super(questionsFilePath);
        this.questionDirBaseName = new File(questionsFilePath).getParent();
    }

    @Override
    protected Question buildQuestion(final String questionText) {
        final Matcher imageMatcher = IMAGE_PATTERN.matcher(questionText);
        if (imageMatcher.find()) {
            final String imageTag = "<img src=\"file://" + questionDirBaseName + "/" + IMAGE_DIR_NAME + "/$1\"/>";
            final String questionTextWithImages = imageMatcher.replaceAll(imageTag);
            return new Question(questionTextWithImages);
        } else {
            return new Question(questionText);
        }
    }
}

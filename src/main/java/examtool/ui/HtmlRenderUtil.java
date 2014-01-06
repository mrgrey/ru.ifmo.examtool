package examtool.ui;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: Yury Chuyko mrgrey@yandex-team.ru
 * Date: 07.01.14
 */
public final class HtmlRenderUtil {

    private HtmlRenderUtil() {
    }

    public static String content(final String... contentParts) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head><body>");
        for (final String contentPart : contentParts) {
            sb.append(contentPart);
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    public static String bold(final String... contents) {
        return "<b>" + StringUtils.join(contents, "") + "</b>";
    }

    public static String line(final String... contents) {
        return StringUtils.join(contents, "") + "<br/>";
    }

}

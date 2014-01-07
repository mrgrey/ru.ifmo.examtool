package examtool.ui;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Author: Yury Chuyko
 * Date: 07.01.14
 */
public final class HtmlRenderUtil {

    private HtmlRenderUtil() {
    }

    public static String content(final String... contentParts) {
        if (contentParts.length > 0) {
            if (contentParts[0].startsWith("<html")) {
                return join(contentParts, "");
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head><body>");
        for (final String contentPart : contentParts) {
            sb.append(contentPart);
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    public static String bold(final String... contents) {
        return "<b>" + join(contents, "") + "</b>";
    }

    public static String line(final String... contents) {
        return join(contents, "") + "<br/>";
    }

    public static String image(final String path) {
        return "<img href=\"file://" + path + "\"/>";
    }

}

package extensions;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.libs.I18N;
import play.templates.JavaExtensions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JodaTimeFormatExtension extends JavaExtensions {

    public static String format(LocalDate date) {
        return format(date, I18N.getDateFormat());
    }

    public static String format(LocalDateTime date) {
        return format(date, I18N.getDateFormat());
    }

    public static String format(ReadablePartial dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(dateTime);
    }
}

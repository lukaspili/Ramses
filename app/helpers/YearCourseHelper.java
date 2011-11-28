package helpers;

import org.joda.time.LocalDate;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearCourseHelper {

    public static int getCurrentYear() {

        LocalDate date = new LocalDate();

        if (date.getMonthOfYear() >= 10) {
            return date.getYear() + 1;
        } else {
            return date.getYear();
        }
    }
}

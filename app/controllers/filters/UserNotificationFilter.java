package controllers.filters;

import controllers.abstracts.UtilController;
import controllers.security.Auth;
import helpers.YearCourseHelper;
import models.school.YearCourse;
import models.user.User;
import play.mvc.Before;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserNotificationFilter extends UtilController {

    @Inject
    private static YearCourseService yearCourseService;

    @Before
    public static void checkNotifications() {

        if (!Auth.isLogged()) {
            return;
        }

        User user = Auth.getCurrentUser();

        int notifications = 0;

        List<YearCourse> userYearCourses = yearCourseService.getUserCoursesForYear(user, YearCourseHelper.getCurrentYear());
        if (!userYearCourses.isEmpty() && !user.hasContract()) {
            flashErrorSamePage("users.contractCadre.needToBeSigned");
            notifications++;
        }

        renderArgs.put("userNotifications", notifications);
    }
}

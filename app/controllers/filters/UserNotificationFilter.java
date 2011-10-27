package controllers.filters;

import controllers.abstracts.UtilController;
import controllers.security.Auth;
import models.school.YearCourse;
import models.user.User;
import play.mvc.Before;
import play.mvc.Controller;
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

        User user = Auth.getCurrentUser();

        int notifications = 0;

        List<YearCourse> userYearCourses = yearCourseService.getUserCoursesForYear(user, yearCourseService.getCurrentYear());
        if (!userYearCourses.isEmpty() && !user.hasContractCadre()) {
            flashErrorSamePage("users.contractCadre.needToBeSigned");
            notifications++;
        }

        renderArgs.put("userNotifications", notifications);
    }
}

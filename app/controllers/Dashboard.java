package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.school.YearCourse;
import models.user.Profile;
import play.mvc.Before;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Dashboard extends AppController {

    @Inject
    private static YearCourseService yearCourseService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("dashboard", renderArgs);
    }

    public static void index() {

        List<YearCourse> yearCourses;

        if (Auth.getCurrentUser().profile == Profile.ADMIN) {
            yearCourses = yearCourseService.getAllCoursesForYear(YearCourseHelper.getCurrentYear());
        } else {
            yearCourses = yearCourseService.getAvailableCoursesForUser(Auth.getCurrentUser());
        }

        render(yearCourses);
    }
}

package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import models.school.Course;
import models.user.Profile;
import play.mvc.Before;
import service.CourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class CoursesAdmin extends AppController {

    @Inject
    private static CourseService courseService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("coursesadmin", renderArgs);
    }

    public static void list() {

        pageHelper.title("list");

        List<Course> courses = courseService.getCoursesWithUsers();

        render(courses);
    }


}

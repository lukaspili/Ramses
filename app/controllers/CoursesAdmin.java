package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import models.school.Course;
import models.user.Profile;
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

    public static void list() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getCoursesWithUsers();

        render(courses);
    }


}

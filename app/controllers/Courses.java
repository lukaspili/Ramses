package controllers;

import controllers.abstracts.AppController;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.school.Course;
import models.school.YearCourse;
import models.user.Profile;
import service.CourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Courses extends AppController {

    @Inject
    private static CourseService courseService;

    public static void candidatures() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getAvailableCoursesByUserAndYear(Auth.getCurrentUser(), YearCourseHelper.getCurrentYear());
        render(courses);
    }

    public static void candidate(long courseId) {

        Course course = Course.findById(courseId);
        notFoundIfNull(course);

        if (!Auth.getCurrentUser().skills.contains(course)) {
            flashError("courses.error.noSkills");
            candidatures();
        }

        if (course.candidates.contains(Auth.getCurrentUser())) {
            flashError("courses.error.candidatureAlreadyExists");
            candidatures();
        }

        courseService.candidate(course, Auth.getCurrentUser());

        flashSuccess("courses.candidate.success");
        candidatures();
    }

    public static void cancelCandidature(long courseId) {

        Course course = Course.findById(courseId);
        notFoundIfNull(course);

        courseService.cancelCandidature(course, Auth.getCurrentUser());

        flashSuccess("courses.cancelCandidature.success");
        candidatures();
    }


}

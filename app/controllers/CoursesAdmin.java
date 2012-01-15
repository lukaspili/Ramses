package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import exceptions.UniqueConstraintException;
import models.school.Course;
import models.school.Promotion;
import models.user.Profile;
import service.CourseService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class CoursesAdmin extends AppController {

    @Inject
    private static CourseService courseService;

    public static void index() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getCoursesWithUsers();

        render(courses);
    }

    public static void newCourse() {

        pageHelper().addActionTitle();

        List<Promotion> promotions = Arrays.asList(Promotion.values());
        render(promotions);
    }

    public static void create(Course course) {

        EnhancedValidator validator = validator().validate(course);
        validator.require("name", "code", "promotion");

        if (!validator.hasErrors()) {
            try {
                courseService.create(course);
            } catch (UniqueConstraintException e) {
                validator.addError("code", "coursesadmin.create.error.code_already_exists");
            }
        }

        if (validator.hasErrors()) {
            List<Promotion> promotions = Arrays.asList(Promotion.values());
            render("CoursesAdmin/newCourse.html", course, promotions);
        }

        flashSuccess("coursesadmin.create.success");
        index();
    }


}

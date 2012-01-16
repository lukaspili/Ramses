package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import exceptions.UniqueConstraintException;
import helpers.YearCourseHelper;
import models.school.Course;
import models.school.Promotion;
import models.school.PromotionCourse;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import service.ContractService;
import service.CourseService;
import service.PrestationService;
import service.YearCourseService;
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

    @Inject
    private static YearCourseService yearCourseService;

    @Inject
    private static ContractService contractService;

    @Inject
    private static PrestationService prestationService;

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

    public static void validateCandidature(long courseId, long userId, long fromYearCourse) {

        Course course = Course.findById(courseId);
        notFoundIfNull(course);

        User user = User.findById(userId);
        notFoundIfNull(user);

        List<YearCourse> yearCourses = yearCourseService.getYearCoursesByCourseAndYear(course, YearCourseHelper.getCurrentYear());

        for (YearCourse yearCourse : yearCourses) {
            for (PromotionCourse promotionCourse : yearCourse.promotionCourses) {
                if (null == prestationService.getPrestationByProfessorAndPromotion(user, promotionCourse)) {
                    prestationService.create(user, promotionCourse, 0);
                }
            }
        }


        if (!user.hasContract()) {
            contractService.createForUser(user);
        }

        course.candidates.remove(user);
        course.save();

        flashSuccess("coursesadmin.validateCandidature.success");

        if (fromYearCourse != 0) {
            YearCoursesAdmin.show(fromYearCourse);
        }

        Dashboard.index();
    }


}

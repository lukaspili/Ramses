package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import exceptions.CoreException;
import helpers.YearCourseHelper;
import models.school.Course;
import models.school.CourseType;
import models.school.YearCourse;
import models.user.Profile;
import play.data.validation.Required;
import service.ContractService;
import service.CourseService;
import service.YearCourseService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class YearCoursesAdmin extends AppController {

    @Inject
    private static YearCourseService yearCourseService;

    @Inject
    private static CourseService courseService;

    @Inject
    private static ContractService contractService;

    public static void index() {

        pageHelper().addActionTitle();

        List<YearCourse> yearCourses = yearCourseService.getYearCoursesByYear(YearCourseHelper.getCurrentYear());
        render(yearCourses);
    }

    public static void newYearCourse() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getAllCourses();
        List<CourseType> courseTypes = CourseType.findAll();
        render(courses, courseTypes);
    }

    public static void create(YearCourse yearCourse, long course, long courseType) {

        pageHelper().addTitleWithController("newYearCourse");

        EnhancedValidator validator = validator().validate(yearCourse);

        if (yearCourse.duration == 0) {
            validator.addError("duration", "validation.required");
        }

        yearCourse.course = Course.findById(course);
        if (null == yearCourse.course) {
            validator.addError("course", "validation.required", true);
        }

        yearCourse.type = CourseType.findById(courseType);
        if (null == yearCourse.type) {
            validator.addError("courseType", "validation.required", true);
        }

        if (validator.hasErrors()) {
            List<Course> courses = courseService.getAllCourses();
            List<CourseType> courseTypes = CourseType.findAll();
            render("YearCoursesAdmin/newYearCourse.html", courses, courseTypes, yearCourse, course, courseType);
        }

        YearCourse yearCourseFromCreate = yearCourseService.create(yearCourse, YearCourseHelper.getCurrentYear());

        show(yearCourseFromCreate.id);
    }

    public static void edit(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);
        notFoundIfNull(yearCourse);

//        if (!yearCourseService.isEditable(yearCourse)) {
//            flashError("coursesAdmin.error.cannot_update_course");
//            show(yearCourse.id);
//        }

        Date startDate = null;
        Date endDate = null;

//        if (yearCourse.isPlannified()) {
//            startDate = new Date(yearCourse.startDate.toDateTimeAtCurrentTime().getMillis());
//            endDate = new Date(yearCourse.endDate.toDateTimeAtCurrentTime().getMillis());
//        }

        render(yearCourse, startDate, endDate);
    }

    public static void update(YearCourse yearCourse, @Required Date startDate, @Required Date endDate) {

        if (null == yearCourse.id) {
            notFound();
        }

        EnhancedValidator validator = validator();

        validator.validate(yearCourse)
                .require("id", "duration");

        if (yearCourse.duration == 0) {
            validator.addError("duration", "validation.required");
        }

        if (validator.hasErrors()) {
            render("YearCoursesAdmin/edit.html", yearCourse, startDate, endDate);
        }

        try {
            yearCourseService.update(yearCourse);
        } catch (CoreException e) {

            if (e.getType() == CoreException.Type.REJECTED) {
                flashError("coursesAdmin.error.cannot_edit_course");
                show(yearCourse.id);

            } else if (e.getType() == CoreException.Type.NULL) {
                notFound();
            }
        }

        show(yearCourse.id);
    }

    public static void show(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);

        if (null == yearCourse) {
            notFound();
        }

        pageHelper().directTitle(yearCourse.course.getFullName());

//        if (yearCourse.hasProfessor() && !yearCourse.professor.hasContract()) {
//            flashErrorSamePage("yearcourses.info.waitingContractCadre");
//        }

        render(yearCourse);
    }

}

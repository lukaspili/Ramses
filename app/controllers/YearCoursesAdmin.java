package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import exceptions.CoreException;
import models.school.YearCourse;
import models.user.Profile;
import org.joda.time.LocalDate;
import play.data.binding.As;
import play.data.validation.Required;
import play.mvc.Before;
import service.YearCourseService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class YearCoursesAdmin extends AppController {

    @Inject
    private static YearCourseService yearCourseService;

    public static void index() {

    }

    public static void newYearCourse() {

    }

    public static void create() {

    }

    public static void edit(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);
        notFoundIfNull(yearCourse);

        if (!yearCourseService.isEditable(yearCourse)) {
            flashError("coursesAdmin.error.cannot_update_course");
            YearCourses.show(yearCourse.id);
        }

        Date startDate = null;
        Date endDate = null;

        if (yearCourse.isPlannified()) {
            startDate = new Date(yearCourse.startDate.toDateTimeAtCurrentTime().getMillis());
            endDate = new Date(yearCourse.endDate.toDateTimeAtCurrentTime().getMillis());
        }

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

        if (!validator.hasErrors()) {
            yearCourse.startDate = new LocalDate(startDate.getTime());
            yearCourse.endDate = new LocalDate(endDate.getTime());

            if (yearCourse.startDate.isAfter(yearCourse.endDate)) {
                validator.addError("endDate", "validation.invalidDate", true);
            }
        }

        if (validator.hasErrors()) {
            render("YearCoursesAdmin/edit.html", yearCourse, startDate, endDate);
        }

        try {
            yearCourseService.update(yearCourse);
        } catch (CoreException e) {

            if (e.getType() == CoreException.Type.REJECTED) {
                flashError("coursesAdmin.error.cannot_edit_course");
                YearCourses.show(yearCourse.id);

            } else if (e.getType() == CoreException.Type.NULL) {
                notFound();
            }
        }

        YearCourses.show(yearCourse.id);
    }
}

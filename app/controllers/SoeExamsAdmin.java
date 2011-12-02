package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import models.contracts.JobOrder;
import models.contracts.JobOrderState;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.data.binding.As;
import play.mvc.Before;
import play.mvc.Util;
import service.JobOrderService;
import service.SoeExamService;
import service.UserService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class SoeExamsAdmin extends AppController {

    @Inject
    private static SoeExamService soeExamService;

    @Inject
    private static UserService userService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("soeExamsAdmin", renderArgs);
    }

    public static void saveFromCourse(SoeExam soe, @As("yyyy-MM-dd") Date date, long courseId) {

        EnhancedValidator validator = validator();

        if (null == date) {
            validator.addError("date", "invalid", true);
            invalidateSave(soe);
        }

        soe.date = new LocalDate(date.getTime());

        YearCourse course = YearCourse.findById(courseId);

        if (null == course) {
            validator.addError("courseId", "required", true);
            invalidateSave(soe);
        }

        soeExamService.create(soe, course, new HashSet<User>());

        flashSuccess("soeExamsAdmin.save.sucess");
        YearCourses.show(courseId);
    }

    @Util
    private static void invalidateSave(SoeExam soe) {
        List<JobOrderState> states = JobOrderState.getList();
        List<User> users = userService.getActiveUsers();
        render("JobOrderAdmin/create.html", states, users, soe);
    }
}

package controllers;

import controllers.YearCourses;
import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import models.contracts.JobOrderState;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.data.binding.As;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Util;
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

    public static void saveFromCourse(SoeExam soeExam, @Required Date date, long courseId) {

        if (validator().validate(soeExam).hasErrors()) {
            invalidateSave(soeExam);
        }

        soeExam.date = new LocalDate(date.getTime());

        YearCourse course = YearCourse.findById(courseId);
        notFoundIfNull(course);

        soeExamService.create(soeExam, course, new HashSet<User>());

        flashSuccess("soeExamsAdmin.save.sucess");
        YearCourses.show(courseId);
    }

    @Util
    private static void invalidateSave(SoeExam soeExam) {
        List<JobOrderState> states = JobOrderState.getList();
        List<User> users = userService.getActiveUsers();
        render("JobOrderAdmin/create.html", states, users, soeExam);
    }
}

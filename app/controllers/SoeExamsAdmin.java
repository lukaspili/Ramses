package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import models.contracts.JobOrderState;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.data.validation.Required;
import play.mvc.Util;
import service.SoeExamService;
import service.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
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

    public static void saveFromCourse(SoeExam soeExam, @Required Date date, long courseId) {

        if (validator().validate(soeExam).hasErrors()) {
            invalidateSave(soeExam);
        }

        soeExam.date = new LocalDate(date.getTime());

        YearCourse course = YearCourse.findById(courseId);
        notFoundIfNull(course);

        soeExamService.create(soeExam, course, new ArrayList<User>());

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

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

        YearCourse yearCourse = YearCourse.findById(courseId);
        notFoundIfNull(yearCourse);

        if (validator().validate(soeExam).hasErrors()) {
            List<JobOrderState> states = JobOrderState.getList();
            List<User> users = userService.getActiveUsers();
            render("YearCoursesAdmin/show.html", states, users, soeExam, yearCourse);
        }

        soeExam.date = new LocalDate(date.getTime());

        soeExamService.create(soeExam, yearCourse, new ArrayList<User>());

        flashSuccess("soeExamsAdmin.save.sucess");
        YearCoursesAdmin.show(courseId);
    }

    @Util
    private static void invalidateSave(SoeExam soeExam) {

    }
}

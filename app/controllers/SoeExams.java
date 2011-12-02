package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import models.school.Course;
import models.school.SoeExam;
import models.school.SoeExamState;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import play.mvc.Before;
import service.SoeExamService;
import service.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class SoeExams extends AppController {

    @Inject
    private static SoeExamService soeExamService;

    @Inject
    private static UserService userService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("soeExam", renderArgs);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void show(long soeId) {

        SoeExam soe = SoeExam.findById(soeId);

        notFoundIfNull(soe);

        pageHelper.uniqueTitle("SOE " + soe.course.course.getFullName());

        render(soe);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void edit(long soeId) {

        SoeExam soe = SoeExam.findById(soeId);

        notFoundIfNull(soe);

        pageHelper.uniqueTitle("SOE " + soe.course.course.getFullName());

        List<SoeExamState> states = soeExamService.getStatesList();
        List<User> users = userService.getActiveUsers();
        List<Long> examinators = collectionHelper.getIdsFromModel(soe.examinators);

        render(soe, states, users, examinators);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void update(SoeExam soe, List<Long> examinators) {

        if (null == examinators) {
            examinators = new ArrayList<Long>();
        }

        Set<User> users = collectionHelper.getFromIds(User.class, examinators);

        try {
            soeExamService.update(soe, users);
        } catch (RuntimeException e) {
            flashError("soe.update.error");
        }

        edit(soe.id);
    }
}

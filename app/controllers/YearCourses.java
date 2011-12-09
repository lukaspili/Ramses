package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import play.mvc.Before;
import service.ContractService;
import service.YearCourseService;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearCourses extends AppController {

    @Inject
    private static YearCourseService yearCourseService;

    @Inject
    private static ContractService contractService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("yearcourses", renderArgs);
    }

    @LoggedAccess
    public static void candidate(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);

        if (null == yearCourse) {
            notFound();
        }

        if (yearCourse.year != YearCourseHelper.getCurrentYear()) {
            flashError("yearcourses.error.wrongYear");
            Dashboard.index();
        }

        if (!Auth.getCurrentUser().skills.contains(yearCourse.course)) {
            flashError("yearcourses.error.noSkills");
            Dashboard.index();
        }

        if (yearCourse.hasProfessor()) {
            flashError("yearcourses.error.professorAlreadyExists");
            Dashboard.index();
        }

        if (yearCourse.candidates.contains(Auth.getCurrentUser())) {
            flashError("yearcourses.error.candidatureAlreadyExists");
            Dashboard.index();
        }

        yearCourseService.candidate(yearCourse, Auth.getCurrentUser());

        flashSuccess("yearcourses.candidate.success");

        Dashboard.index();
    }

    @LoggedAccess
    public static void cancelCandidature(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);

        if (null == yearCourse) {
            notFound();
        }

        if (yearCourse.hasProfessor()) {
            flashError("yearcourses.error.professorAlreadyExists");
            Dashboard.index();
        }

        yearCourseService.cancelCandidature(yearCourse, Auth.getCurrentUser());

        flashSuccess("yearcourses.cancelCandidature.success");

        Dashboard.index();
    }

    @LoggedAccess(Profile.ADMIN)
    public static void show(Long yearCourseId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);

        if (null == yearCourse) {
            notFound();
        }

        pageHelper.uniqueTitle(yearCourse.course.getFullName());

        if (yearCourse.hasProfessor() && !yearCourse.professor.hasContract()) {
            flashErrorSamePage("yearcourses.user.waitingContractCadre");
        }

        render(yearCourse);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void validateCandidature(Long yearCourseId, Long userId) {

        YearCourse yearCourse = YearCourse.findById(yearCourseId);
        User user = User.findById(userId);

        if (null == yearCourse || null == user) {
            notFound();
        }

        if (yearCourse.hasProfessor()) {
            flashError("yearcourses.error.professorAlreadyExists");
            show(yearCourse.id);
        }

        if (!yearCourse.candidates.contains(user)) {
            flashError("yearcourses.error.userDontCandidate");
            show(yearCourse.id);
        }

        if (!user.skills.contains(yearCourse.course)) {
            flashError("yearcourses.error.userHaveNoSkill");
            show(yearCourse.id);
        }

        yearCourseService.validateCandidature(yearCourse, user);

        if (!user.hasContract()) {
            contractService.createForUser(user);
        }

        flashSuccess("yearcourses.validateCandidature.success");
        show(yearCourse.id);
    }
}

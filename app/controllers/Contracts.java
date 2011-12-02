package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.contracts.ContractState;
import models.school.YearCourse;
import models.user.User;
import play.mvc.Before;
import service.ContractService;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Contracts extends AppController {

    private static PageHelper pageHelper;

    @Inject
    private static ContractService contractService;

    @Inject
    private static YearCourseService yearCourseService;

    @Before
    public static void before() {
        pageHelper = new PageHelper("contracts", renderArgs);
    }

    public static void view() {

        User user = Auth.getCurrentUser();

        // user have no contract
        if (!user.hasContract()) {
            flashError("user.error.have_no_contract");
            Dashboard.index();
        }

        ContractState contractState = user.contract.state;

        render(contractState);
    }

    public static void download() {

        User user = Auth.getCurrentUser();

        // user have no contract
        if (!user.hasContract()) {
            flashError("contracts.error.noContract");
            Dashboard.index();
        }

        response.setContentTypeIfNotSet(user.contract.pdf.type());
        renderBinary(user.contract.pdf.get(), "Contrat cadre.pdf");
    }

    public static void regenerate() {

        User user = Auth.getCurrentUser();

        // user have no courses
        List<YearCourse> userYearCourses = yearCourseService.getUserCoursesForYear(user, YearCourseHelper.getCurrentYear());

        if (userYearCourses.isEmpty()) {
            flashError("contracts.error.noCourses");
            Dashboard.index();
        }

        if (user.hasContract() && user.contract.state != ContractState.CREATED) {
            flashError("contract.regenerate.contractAlreadySigned");
            Dashboard.index();
        }

        contractService.createForUser(user);

        flashSuccess("contract.regenerate.success");
        view();
    }

}

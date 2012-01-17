package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.contracts.SpecificPrestation;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.data.validation.Required;
import service.SpecificPrestationService;
import service.UserService;
import service.YearCourseService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@LoggedAccess(Profile.ADMIN)
public class SpecificPrestationsAdmin extends AppController {

    @Inject
    private static UserService userService;

    @Inject
    private static SpecificPrestationService specificPrestationService;

    @Inject
    private static YearCourseService yearCourseService;

    public static void index() {

        pageHelper().addActionTitle();

        List<SpecificPrestation> specificPrestations = specificPrestationService.getSpecificPrestationsWithUserAndYearCourse();
        render(specificPrestations);
    }

    public static void newPrestation() {

        pageHelper().addActionTitle();

        List<YearCourse> yearCourses = yearCourseService.getYearCoursesByYear(YearCourseHelper.getCurrentYear());
        List<User> usersModel = userService.getActiveUsers();
        List<Long> users = new ArrayList<Long>();

        render(yearCourses, users, usersModel);
    }

    public static void create(SpecificPrestation specificPrestation, @Required Date date, List<Long> users, long yearCourse) {

        if (null == users) {
            users = new ArrayList<Long>();
        }

        if (null == specificPrestation) {
            specificPrestation = new SpecificPrestation();
        }

        List<User> usersFromSelect = new ArrayList<User>();
        YearCourse yearCourseFromSelect = null;

        EnhancedValidator validator = validator().validate(specificPrestation)
                .require("title");

        if (specificPrestation.hours == 0) {
            validator.addError("hours", "validation.required");
        }

        if (specificPrestation.price == 0) {
            validator.addError("price", "validation.required");
        }

        if (!validator.hasErrors()) {

            yearCourseFromSelect = YearCourse.findById(yearCourse);
            if(null == yearCourseFromSelect) {
                validator.addError("yearCourse", "validation.required", true);
            }

            usersFromSelect = userService.getActiveUsersByIds(users);
            if (usersFromSelect.isEmpty()) {
                validator.addError("users", "validation.required", true);
            }
        }

        if (validator.hasErrors()) {
            pageHelper().addTitleWithController("newPrestation");
            List<YearCourse> yearCourses = yearCourseService.getYearCoursesByYear(YearCourseHelper.getCurrentYear());
            List<User> usersModel = userService.getActiveUsers();
            render("SpecificPrestationsAdmin/newPrestation.html", yearCourses, users, usersModel, specificPrestation, date);
        }

        specificPrestation.date = new LocalDate(date);
        specificPrestation.yearCourse = yearCourseFromSelect;

        for (User user : usersFromSelect) {
            specificPrestation.user = user;
            specificPrestationService.create(specificPrestation);
        }

        flashSuccess("specificprestationsadmin.create.success");
        newPrestation();
    }
}

package controllers;

import controllers.abstracts.AppController;
import controllers.filters.UserFirstLogin;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import controllers.security.PublicAccess;
import models.school.Course;
import models.user.User;
import org.apache.commons.lang3.StringUtils;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Util;
import service.CourseService;
import service.UserService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Users extends AppController {

    @Inject
    private static UserService userService;

    @Inject
    private static CourseService courseService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("users", renderArgs);
    }

    @PublicAccess(only = true)
    public static void login() {

        pageHelper.uniqueTitle("users.login");

        render();
    }

    @PublicAccess(only = true)
    public static void authenticate(User user) {

        EnhancedValidator validator = validator();

        if (validator.validate(user).require("idBooster", "password").hasErrors()) {
            user.password = null;
            render("Users/login.html", user);
        }

        User userFromDb = userService.getFromLogin(user.idBooster, user.password);

        if (null == userFromDb) {
            user.password = null;
            flashErrorSamePage("users.login.authentication.failure");
            render("Users/login.html", user);
        }

        if (userFromDb.desactivated) {
            user.password = null;
            flashErrorSamePage("users.authenticate.error.user_is_desactivated");
            render("Users/login.html", user);
        }

        Auth.loginUser(userFromDb);

        if (!userFromDb.active) {
            firstLogin();
        }

        Dashboard.index();
    }

    @LoggedAccess
    @UserFirstLogin
    public static void logout() {
        Auth.logoutUser();
        flash.clear();
        Users.login();
    }

    @LoggedAccess
    @UserFirstLogin(only = true)
    public static void firstLogin() {

        pageHelper.uniqueTitle("users.firstLogin");

        List<Course> courses = courseService.getCourses();
        List<Long> skills = new ArrayList<Long>();

        render(courses, skills);
    }

    @LoggedAccess
    @UserFirstLogin(only = true)
    public static void completeFirstLogin(User user, String passwordConfirmation, List<Long> skills) {

        if (null == skills) {
            skills = new ArrayList<Long>();
        }

        EnhancedValidator validator = validator();
        validator.validate(user)
                .require("password")
                .requireFields(passwordConfirmation);

        requirePersonalInfo(validator);

        if (validator.hasErrors()) {
            user.password = null;
            List<Course> courses = courseService.getCourses();
            render("Users/firstLogin.html", courses, user, skills);
        }

        if (!StringUtils.equals(user.password, passwordConfirmation)) {

            validator.addError("passwordConfirmation", "users.edit.error.passwordConfirmation", true);

            user.password = null;
            List<Course> courses = courseService.getCourses();
            render("Users/firstLogin.html", courses, user, skills);
        }

        Set<Course> courses = collectionHelper.getFromIds(Course.class, skills);

        userService.updateFromFirstLogin(user, courses, Auth.getCurrentUser());

        Dashboard.index();
    }

    @LoggedAccess
    public static void editPersonalInfo() {

        pageHelper.uniqueTitle("users.editPersonalInfo");

        User user = Auth.getCurrentUser();
        render(user);
    }

    @LoggedAccess
    public static void savePersonalInfo(User user) {

        EnhancedValidator validator = validator();
        validator.validate(user);

        requirePersonalInfo(validator);

        if (validator.hasErrors()) {
            render("Users/editPersonalInfo.html", user);
        }

        userService.updateFromPersonalInfo(user, Auth.getCurrentUser());

        flashSuccess("users.savePersonalInfo.success");

        Dashboard.index();
    }

    @LoggedAccess
    public static void editPassword() {

        pageHelper.uniqueTitle("users.editPassword");

        render();
    }

    @LoggedAccess
    public static void savePassword(User user, @Required String passwordConfirmation) {

        EnhancedValidator validator = validator();

        if (validator.validate(user).require("password").hasErrors()) {
            render("Users/editPassword.html");
        }

        if (!StringUtils.equals(user.password, passwordConfirmation)) {
            validator.addError("passwordConfirmation", "users.error.passwordConfirmation", true).save();
            render("Users/editPassword.html");
        }

        userService.updateFromPassword(user.password, Auth.getCurrentUser());

        flashSuccess("users.savePassword.success");

        Dashboard.index();
    }

    @LoggedAccess
    public static void editSkills() {

        pageHelper.uniqueTitle("users.editSkills");

        List<Course> courses = courseService.getCourses();
        List<Long> skills = collectionHelper.getIdsFromModel(Auth.getCurrentUser().skills);

        render(courses, skills);
    }

    @LoggedAccess
    public static void saveSkills(List<Long> skills) {

        Set<Course> courses = collectionHelper.getFromIds(Course.class, skills);

        userService.updateFromSkills(courses, Auth.getCurrentUser());

        flashSuccess("users.saveSkills.success");

        Dashboard.index();
    }

    @Util
    private static void requirePersonalInfo(EnhancedValidator validator) {
        validator.require("firstName", "lastName", "street", "postalCode", "city", "siret", "rcs");
    }
}

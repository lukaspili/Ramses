package controllers;

import controllers.abstracts.AppController;
import controllers.filters.UserFirstLogin;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import controllers.security.PublicAccess;
import models.school.Course;
import models.user.User;
import notifiers.Mails;
import org.apache.commons.lang3.StringUtils;
import play.data.validation.Required;
import play.mvc.Util;
import service.CourseService;
import service.UserService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Users extends AppController {

    @Inject
    private static UserService userService;

    @Inject
    private static CourseService courseService;

    @PublicAccess(only = true)
    public static void login() {

        pageHelper().addActionTitle();

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

    @PublicAccess(only = true)
    public static void forgotPassword() {

        pageHelper().addActionTitle();

        render();
    }

    @PublicAccess(only = true)
    public static void forgotPasswordRequest(@Required String idBooster) {

        if (validation.hasErrors()) {
            render("Users/forgotPassword.html");
        }

        User user = User.find("byIdBooster", idBooster).first();
        notFoundIfNull(user);

        userService.forgotPasswordRequest(user);

        Mails.forgotPasswordRequest(user);

        flashSuccess("users.forgotPasswordRequest.success");
        login();
    }

    @PublicAccess(only = true)
    public static void resetPassword(@Required String idBooster, @Required String key) {

        if (validation.hasErrors()) {
            notFound();
        }

        User user = checkValidResetPassword(idBooster, key);

        renderArgs.put("idBooster", idBooster);
        renderArgs.put("key", key);

        render(user);
    }

    @PublicAccess(only = true)
    public static void resetPasswordRequest(@Required String idBooster, @Required String key, @Required String password, @Required String passwordConfirm) {

        if (validation.hasErrors()) {
            render("Users/resetPassword.html", idBooster, key);
        }

        if (!password.equals(passwordConfirm)) {
            validation.addError("passwordConfirm", "users.error.passwordConfirmation");
            render("Users/resetPassword.html", idBooster, key);
        }

        User user = checkValidResetPassword(idBooster, key);

        userService.resetPassword(user, password);

        flashSuccess("users.resetPasswordRequest.success");
        login();
    }

    @Util
    public static User checkValidResetPassword(String idBooster, String key) {

        User user = User.find("byIdBooster", idBooster).first();
        notFoundIfNull(user);

        if (!user.hasPasswordReset() || !user.passwordResetKey.equals(key) || user.passwordResetDate.isAfter(user.passwordResetDate.plusDays(1))) {
            notFound();
        }

        return user;
    }

    @LoggedAccess
    @UserFirstLogin(only = true)
    public static void firstLogin() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getAllCourses();
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
            List<Course> courses = courseService.getAllCourses();
            render("Users/firstLogin.html", courses, user, skills);
        }

        if (!StringUtils.equals(user.password, passwordConfirmation)) {

            validator.addError("passwordConfirmation", "users.edit.error.passwordConfirmation", true);

            user.password = null;
            List<Course> courses = courseService.getAllCourses();
            render("Users/firstLogin.html", courses, user, skills);
        }

        List<Course> courses = collectionHelper.getFromIds(Course.class, skills);

        userService.updateFromFirstLogin(user, courses, Auth.getCurrentUser());

        Dashboard.index();
    }

    @LoggedAccess
    public static void editPersonalInfo() {

        pageHelper().addActionTitle();

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

        pageHelper().addActionTitle();

        render();
    }

    @LoggedAccess
    public static void savePassword(User user, @Required String passwordConfirmation) {

        EnhancedValidator validator = validator();

        if (validator.validate(user).require("password").hasErrors()) {
            render("Users/editPassword.html");
        }

        if (!StringUtils.equals(user.password, passwordConfirmation)) {
            validator.addError("passwordConfirmation", "users.error.passwordConfirmation", true);
            render("Users/editPassword.html");
        }

        userService.updateFromPassword(user.password, Auth.getCurrentUser());

        flashSuccess("users.savePassword.success");

        Dashboard.index();
    }

    @LoggedAccess
    public static void editSkills() {

        pageHelper().addActionTitle();

        List<Course> courses = courseService.getAllCourses();
        List<Long> skills = collectionHelper.getIdsFromModel(Auth.getCurrentUser().skills);

        render(courses, skills);
    }

    @LoggedAccess
    public static void saveSkills(List<Long> skills) {

        List<Course> courses = collectionHelper.getFromIds(Course.class, skills);

        userService.updateFromSkills(courses, Auth.getCurrentUser());

        flashSuccess("users.saveSkills.success");

        Dashboard.index();
    }

    @Util
    private static void requirePersonalInfo(EnhancedValidator validator) {
        validator.require("firstName", "lastName", "street", "postalCode", "city", "siret", "rcs");
    }
}

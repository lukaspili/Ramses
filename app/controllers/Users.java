package controllers;

import controllers.abstracts.AppController;
import controllers.abstracts.UtilController;
import controllers.filters.UserFirstLoginAccess;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import controllers.security.PublicAccess;
import models.user.User;
import play.mvc.Before;
import service.UserService;
import validation.EnhancedValidator;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Users extends AppController {

    @Inject
    private static UserService userService = new UserService();

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("users", renderArgs);
    }

    @PublicAccess(only = true)
    public static void login() {
        pageHelper.uniqueTitle("users.login");

        if (flash.contains("user.password")) {
            flash.remove("user.password");
        }

        render();
    }

    @PublicAccess(only = true)
    public static void authenticate(User user) {

        EnhancedValidator validator = validator();

        if (validator.validate(user).require("idBooster", "password").hasErrors()) {
            login();
        }

        user = userService.getFromLogin(user.idBooster, user.password);

        if (null == user) {
            params.flash();
            flashError("users.login.authentication.failure");
            login();
        }

        Auth.loginUser(user);

        if (!user.active) {
            firstLogin();
        }

        Dashboard.index();
    }

    @LoggedAccess
    @UserFirstLoginAccess
    public static void logout() {
        Auth.logoutUser();
        Users.login();
    }

    @LoggedAccess
    @UserFirstLoginAccess
    public static void firstLogin() {
        renderText("first login");
    }

    @LoggedAccess
    public static void show(Long id) {

    }
}

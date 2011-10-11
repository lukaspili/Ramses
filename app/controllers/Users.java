package controllers;

import controllers.abstracts.AppController;
import controllers.abstracts.UtilController;
import service.UserService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Users extends AppController {

    @Inject
    private static UserService userService = new UserService();

    @Check(Auth.GUEST_PROFILE)
    public static void login() {
        render();
    }

    @Check(Auth.GUEST_PROFILE)
    public static void authenticate() {

    }

    public static void logout() {

    }

    public static void firstLogin() {

    }
}

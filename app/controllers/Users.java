package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.With;
import service.UserService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Users extends Controller {

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

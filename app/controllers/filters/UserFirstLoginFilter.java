package controllers.filters;

import controllers.security.Auth;
import controllers.Users;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserFirstLoginFilter extends Controller {

    @Before
    public static void checkFirstLogin() {

        if (null != getActionAnnotation(UserFirstLoginAccess.class)) {
            return;
        }

        if (!Auth.isLogged() || Auth.getCurrentUser().active) {
            return;
        }

        Users.firstLogin();
    }
}

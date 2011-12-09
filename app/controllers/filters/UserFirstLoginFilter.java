package controllers.filters;

import controllers.Dashboard;
import controllers.security.Auth;
import controllers.Users;
import controllers.security.PublicAccess;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserFirstLoginFilter extends Controller {

    @Before
    public static void checkFirstLogin() {

        // user is not logged or the action is public, ignore
        if (!Auth.isLogged() || null != getActionAnnotation(PublicAccess.class)) {
            return;
        }

        UserFirstLogin firstLogin = getActionAnnotation(UserFirstLogin.class);

        // user is not active and action is not for first logged users
        if (null == firstLogin && !Auth.getCurrentUser().active) {
            Users.firstLogin();
        }

        // user is active and action is for first logged users only
        if (null != firstLogin && firstLogin.only() && Auth.getCurrentUser().active) {
            Dashboard.index();
        }
    }
}

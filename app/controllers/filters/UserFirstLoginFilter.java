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

        if (!Auth.isLogged() || null != getActionAnnotation(PublicAccess.class)) {
            return;
        }

        if (null == getActionAnnotation(UserFirstLoginOnly.class) && !Auth.getCurrentUser().active) {
            Users.firstLogin();
        }

        if (null != getActionAnnotation(UserFirstLoginOnly.class) && Auth.getCurrentUser().active) {
            Dashboard.index();
        }
    }
}

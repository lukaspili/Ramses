package controllers.filters;

import controllers.Dashboard;
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

        if (null != getActionAnnotation(UserFirstLoginOnly.class) && Auth.getCurrentUser().active) {
            Dashboard.index();
        }
    }
}

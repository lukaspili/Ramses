package controllers.helper;

import controllers.security.Auth;
import models.user.Profile;
import play.mvc.Before;
import play.mvc.Controller;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class AuthHelper extends Controller {


    public static final String AUTH_HELPER = "auth";

    private Profile profile;

    private AuthHelper() {

    }

    @Before
    public static void before() {

        AuthHelper helper;

        if (Auth.isLogged()) {
            helper = new AuthHelper(Auth.getCurrentUser().profile);
        } else {
            helper = new GuestAuthHelper();
        }

        renderArgs.put(AUTH_HELPER, helper);
    }

    private AuthHelper(Profile profile) {
        this.profile = checkNotNull(profile);
    }

    public boolean isAdmin() {
        return profile.equals(Profile.ADMIN);
    }

    public boolean isLogged() {
        return true;
    }

    public static class GuestAuthHelper extends AuthHelper {

        @Override
        public boolean isAdmin() {
            return false;
        }

        @Override
        public boolean isLogged() {
            return false;
        }
    }
}

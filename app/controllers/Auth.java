package controllers;

import models.user.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Util;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Auth extends Secure.Security {

    private static final String CURRENT_USER = "user";

    public static final String GUEST = "user";

    @Before
    public static void checkUser() {

        if (!isLogged()) {
            return;
        }

        Long id = Long.valueOf(session.get(CURRENT_USER));
        User user = User.findById(id);

        if (null == user) {
            Logger.error("User don't exists from session id : " + id);
            logoutUser();
        }

        renderArgs.put(CURRENT_USER, user);
    }

    @Util
    public static User getCurrentUser() {
        return renderArgs.get(Auth.CURRENT_USER, User.class);
    }

    @Util
    public static boolean isLogged() {
        return session.contains(CURRENT_USER);
    }

    @Util
    public static void logoutUser() {
        session.remove(CURRENT_USER);
        renderArgs.put(CURRENT_USER, null);
    }


//    @Before(unless = {"login", "authenticate"})
//    public static void checkAuth() {
//
//        String id = session.get(Auth.CURRENT_USER);
//
//        if (StringUtils.isBlank()) {
//            login();
//        }
//
//        User user = User.find("byEmail", email).first();
//        renderArgs.put(Auth.CURRENT_USER, user);
//
//        Check check = getActionAnnotation(Check.class);
//        if (check != null) {
//            check(check);
//        }
//        check = getControllerInheritedAnnotation(Check.class);
//        if (check != null) {
//            check(check);
//        }
//    }
//
//    private static void check(Check check) {
//
//        String userProfile = getCurrentUser().profile;
//        Logger.debug("Checking rights for user " + getCurrentUser().getName() + " with profile " + userProfile);
//
//        for (String profile : check.value()) {
//
//            Logger.debug("Checking if current user has right : " + profile);
//
//            if (!userProfile.equals(profile)) {
//                forbidden("Access denied : You don't have enought access");
//            }
//
//            Logger.debug("Right is OK");
//        }
//    }
//
//    public static boolean check(String profile) {
//
//        if (profile.equals("admin"))
//            return session.get("username").equals("admin");
//        return false;
//    }
//
//
//    @Before(only = {"login", "authenticate"})
//    public static void checkLogged() {
//
//        if (null != session.get("user")) {
//            Dashboard.client();
//        }
//    }
//
//
//    public static void login() {
//        render();
//    }
//
//    public static void authenticate(@Required @Email String login, @Required @Password String password) {
//
//        if (validation.hasErrors()) {
//            render("Auth/login.html");
//        }
//
//        User user = null;
//
//        try {
//            user = UserService.authenticate(login, password);
//        } catch (CoreException e) {
//            flash.put("error", "Invalid credentials");
//            render("Auth/login.html");
//        }
//
//        session.put(Auth.CURRENT_USER, user.email);
//        Dashboard.index();
//    }
//
//    public static void logout() {
//        session.clear();
//        Dashboard.index();
//    }
}

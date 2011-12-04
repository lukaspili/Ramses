package controllers;

import controllers.abstracts.UtilController;
import controllers.security.LoggedAccess;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Application extends UtilController {



    @LoggedAccess
    public static void about() {

    }

    @LoggedAccess
    public static void reportBug() {

    }
}
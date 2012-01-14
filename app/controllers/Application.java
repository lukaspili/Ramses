package controllers;

import controllers.abstracts.AppController;
import controllers.abstracts.UtilController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import controllers.security.PublicAccess;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Application extends AppController {

    public static void about() {

        pageHelper().addActionTitle();

        render();
    }

    public static void reportBug() {

        pageHelper().addActionTitle();

        render();
    }
}
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

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("users", renderArgs);
    }

    public static void about() {

        pageHelper.uniqueTitle("application.about");

        render();
    }

    public static void reportBug() {

        pageHelper.uniqueTitle("application.reportBug");

        render();
    }
}
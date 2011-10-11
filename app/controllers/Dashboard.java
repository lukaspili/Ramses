package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import play.mvc.Before;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Dashboard extends AppController {

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("dashboard", renderArgs);
    }

    public static void index() {
        render();
    }
}

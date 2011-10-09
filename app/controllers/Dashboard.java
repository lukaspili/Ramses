package controllers;

import controllers.helper.PageHelper;
import play.mvc.Before;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Dashboard extends AbstractController {

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("dashboard", renderArgs);
    }

    public static void index() {
        render();
    }
}

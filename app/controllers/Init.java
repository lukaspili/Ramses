package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.user.User;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@PublicAccess
public class Init extends UtilController {

    public static void index() {

        if (User.count() == 0) {
            DatabaseInitializer.initDatabaseWithTests();
        }

        Dashboard.index();
    }
}

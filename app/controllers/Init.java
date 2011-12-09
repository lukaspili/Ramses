package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.user.User;
import notifiers.Mails;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@PublicAccess
public class Init extends UtilController {

    public static void dev() {

        if (User.count() == 0) {
            DatabaseInitializer.initDatabaseForDev();
        }

        Dashboard.index();
    }

    public static void prod() {

        if (User.count() == 0) {
            DatabaseInitializer.initDatabaseForProd();
        }

        Dashboard.index();
    }

    public static void mail() {

        User user = User.find("byIdBooster", "75054").first();
        Mails.register(user);

        ok();
    }
}

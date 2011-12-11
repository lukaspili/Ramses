package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.user.Profile;
import models.user.User;
import notifiers.Mails;
import play.Logger;
import play.Play;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@PublicAccess
public class Init extends UtilController {

    public static void dev() {

        if (Play.configuration.getProperty("application.mode").equals("prod")) {
            Logger.info("Trying to init dev database on production mode");
            return;
        }

        if (User.count() == 0) {
            DatabaseInitializer.initDatabaseForDev();
        }

        Dashboard.index();
    }
}

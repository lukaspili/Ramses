package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.user.Profile;
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

    public static void prod(String password) {

        if (null != password && password.equals("fofobabar") && User.count() == 0) {
            DatabaseInitializer.initDatabaseForProd();
        }

        Dashboard.index();
    }

    public static void mail() {

        User user = User.find("byIdBooster", "75054").first();
        Mails.register(user);

        ok();
    }

    public static void update1(String password) {

        if (null != password && password.equals("fofobabar") && User.count() == 0) {

            User user = User.find("byIdBooster", 75054).first();
            user.profile = Profile.ADMIN;
            user.save();

            User seb = new User("59032", Profile.ADMIN);
            seb.save();

            Mails.register(seb);
        }

        Dashboard.index();
    }
}

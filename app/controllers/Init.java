package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.contracts.ContractState;
import models.user.Profile;
import models.user.User;
import notifiers.Mails;
import play.Logger;
import play.Play;
import service.ContractService;

import java.util.List;

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

    public static void regenerate(String password) {

        ContractService service = new ContractService();

        if (null == password || !password.equals("fofobabar")) {
            forbidden();
        }

        for (User u : User.<User>findAll()) {

            if (u.hasContract()) {
                Logger.info("INIT - Regenerate contract for user : " + u);
                service.createForUser(u);
            } else {
                Logger.info("INIT - No contract to regenerate for user : " + u);
            }
        }

        ok();
    }

    public static void update(String password) {


        if (null == password || !password.equals("fofobabar")) {
            forbidden();
        }

        User last = User.find("order by staNumber desc limit 1").first();

        User user1 = User.find("byIdBooster", "59032").first();
        user1.staNumber = last.staNumber + 1;
        user1.save();

        User user2 = User.find("byIdBooster", "43974").first();
        user2.staNumber = last.staNumber + 2;
        user2.save();

        ok();
    }
}

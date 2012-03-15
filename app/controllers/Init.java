package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import models.contracts.Contract;
import models.contracts.ContractState;
import models.user.User;
import play.Logger;
import play.Play;
import service.ContractService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@PublicAccess
public class Init extends UtilController {

//    public static void dev() {
//
//        if (Play.configuration.getProperty("application.mode").equals("prod")) {
//            Logger.info("Trying to init dev database on production mode");
//            return;
//        }
//
//        if (User.count() == 0) {
//            DatabaseInitializer.initDatabaseForDev();
//        }
//
//        Dashboard.index();
//    }

    public static void regenerate(String password) {

        ContractService service = new ContractService();

        if (null == password || !password.equals("fofobabar")) {
            forbidden();
        }

        for (User u : User.<User>findAll()) {

            if (!u.active) {
                Logger.info("INIT - User is not active");
                continue;
            }

            ContractState state = null;
            if (u.hasContract()) {
                state = u.contract.state;
            }

            Logger.info("INIT - Generate contract for user");
            Contract contract = service.createForUser(u);

            if (state != null) {
                Logger.info("INIT - Contract state is : " + state.getLabel());
                contract.state = state;
                contract.save();
            }
        }

        ok();
    }

//    public static void update(String password) {
//
//
//        if (null == password || !password.equals("fofobabar")) {
//            forbidden();
//        }
//
//        User last = User.find("order by staNumber desc limit 1").first();
//
//        User user1 = User.find("byIdBooster", "59032").first();
//        user1.staNumber = last.staNumber + 1;
//        user1.save();
//
//        User user2 = User.find("byIdBooster", "43974").first();
//        user2.staNumber = last.staNumber + 2;
//        user2.save();
//
//        ok();
//    }
}

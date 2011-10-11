import controllers.Users;
import models.user.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() throws Exception {

        if (User.count() == 0) {
            Logger.debug("Load fixtures into database");
            Fixtures.loadModels("fixtures.yml");

            Logger.debug("Users in database :");
            for (User user : User.<User>findAll()) {
                Logger.debug("User %s : %s", user.id, user);
            }
        }
    }
}

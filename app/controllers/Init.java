package controllers;

import controllers.abstracts.UtilController;
import controllers.security.PublicAccess;
import init.DatabaseInitializer;
import models.user.Profile;
import models.user.User;
import notifiers.Mails;
import play.Logger;
import play.Play;

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

    public static void number(String password) {

        if(null == password || !password.equals("fofobabar")) {
            forbidden();
        }

        User user = User.find("byIdBooster", "75054").first();
        user.staNumber = 1;
        user.save();

        User user2 = User.find("byIdBooster", "76330").first();
        user2.staNumber = 2;
        user2.save();

        User user3 = User.find("byIdBooster", "83026").first();
        user3.staNumber = 3;
        user3.save();

        User user4 = User.find("byIdBooster", "68621").first();
        user4.staNumber = 4;
        user4.save();

        User user5 = User.find("byIdBooster", "78349").first();
        user5.staNumber = 5;
        user5.save();

        User user6 = User.find("byIdBooster", "76179").first();
        user6.staNumber = 6;
        user6.save();

        User user7 = User.find("byIdBooster", "68541").first();
        user7.staNumber = 7;
        user7.save();

        User user8 = User.find("byIdBooster", "77014").first();
        user8.staNumber = 8;
        user8.save();

        User user9 = User.find("byIdBooster", "60371").first();
        user9.staNumber = 9;
        user9.save();

        User user10 = User.find("byIdBooster", "68715").first();
        user10.staNumber = 10;
        user10.save();

        User user11 = User.find("byIdBooster", "97310").first();
        user11.staNumber = 11;
        user11.save();

        User user12 = User.find("byIdBooster", "59260").first();
        user12.staNumber = 12;
        user12.save();

        User user13 = User.find("byIdBooster", "74861").first();
        user13.staNumber = 13;
        user13.save();

        User user14 = User.find("byIdBooster", "124407").first();
        user14.staNumber = 17;
        user14.save();

        User user15 = User.find("byIdBooster", "92539").first();
        user15.staNumber = 18;
        user15.save();

        User user16 = User.find("byIdBooster", "92839").first();
        user16.staNumber = 19;
        user16.save();

        User user17 = User.find("byIdBooster", "129165").first();
        user17.staNumber = 20;
        user17.save();

        User user18 = User.find("byIdBooster", "106003").first();
        user18.staNumber = 21;
        user18.save();

        User user19 = User.find("byIdBooster", "79786").first();
        user19.staNumber = 22;
        user19.save();

        User user20 = User.find("byIdBooster", "111555").first();
        user20.staNumber = 23;
        user20.save();

        User user21 = User.find("byIdBooster", "90352").first();
        user21.staNumber = 24;
        user21.save();

        User user22 = User.find("byIdBooster", "72779").first();
        user22.staNumber = 25;
        user22.save();

        User user23 = User.find("byIdBooster", "76077").first();
        user23.staNumber = 26;
        user23.save();

        User user24 = User.find("byIdBooster", "58954").first();
        user24.staNumber = 27;
        user24.save();

        User user25 = User.find("byIdBooster", "68341").first();
        user25.staNumber = 28;
        user25.save();

        User user26 = User.find("byIdBooster", "75667").first();
        user26.staNumber = 29;
        user26.save();

        User user27 = User.find("byIdBooster", "106139").first();
        user27.staNumber = 30;
        user27.save();

        User user28 = User.find("byIdBooster", "115732").first();
        user28.staNumber = 31;
        user28.save();

        User user29 = User.find("byIdBooster", "128771").first();
        user29.staNumber = 32;
        user29.save();

        User user30 = User.find("byIdBooster", "128921").first();
        user30.staNumber = 33;
        user30.save();

        User user31 = User.find("byIdBooster", "131468").first();
        user31.staNumber = 34;
        user31.save();

        User user32 = User.find("byIdBooster", "93645").first();
        user32.staNumber = 35;
        user32.save();

        User user33 = User.find("byIdBooster", "68928").first();
        user33.staNumber = 36;
        user33.save();

        User user34 = User.find("byIdBooster", "68423").first();
        user34.staNumber = 37;
        user34.save();

        User user35 = User.find("byIdBooster", "78302").first();
        user35.staNumber = 38;
        user35.save();

        User user36 = User.find("byIdBooster", "125516").first();
        user36.staNumber = 39;
        user36.save();

        User user37 = User.find("byIdBooster", "95424").first();
        user37.staNumber = 40;
        user37.save();

        User user38 = User.find("byIdBooster", "136135").first();
        user38.staNumber = 41;
        user38.save();

        User user39 = User.find("byIdBooster", "115634").first();
        user39.staNumber = 42;
        user39.save();

        User user40 = User.find("byIdBooster", "68470").first();
        user40.staNumber = 43;
        user40.save();

        User user41 = User.find("byIdBooster", "75748").first();
        user41.staNumber = 44;
        user41.save();

        User user42 = User.find("byIdBooster", "68852").first();
        user42.staNumber = 45;
        user42.save();

        User user43 = User.find("byIdBooster", "130762").first();
        user43.staNumber = 46;
        user43.save();

        User user44 = User.find("byIdBooster", "133827").first();
        user44.staNumber = 47;
        user44.save();

        List<User> users = User.find("byStaNumber", 0L).fetch();
        int i = 48;
        for(User u : users) {
            u.staNumber = i;
            u.save();
            i++;
        }

        ok();
    }
}

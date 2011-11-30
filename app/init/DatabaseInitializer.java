package init;

import models.school.Course;
import models.school.SoeExam;
import models.school.SoeExamState;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DatabaseInitializer {

    public static void initDatabase() {

        Logger.debug("Init database...");

        YearCourse course2SUN = new YearCourse();
        course2SUN.year = 2012;
        course2SUN.duration = 40;
        course2SUN.startDate = new LocalDate();
        course2SUN.endDate = new LocalDate();
        course2SUN.course = Course.find("byCode", "2SUN").first();
        course2SUN.save();

        User user75054 = User.find("byIdBooster", "75054").first();
        User user8080 = User.find("byIdBooster", "8080").first();

        Set<User> examinators1 = new HashSet<User>();
        examinators1.add(user75054);
        examinators1.add(user8080);

        Set<User> examinators2 = new HashSet<User>();
        examinators2.add(user75054);

        SoeExam soe2SUN1 = new SoeExam();
        soe2SUN1.course = course2SUN;
        soe2SUN1.date = new LocalDate();
        soe2SUN1.plannifiedDuration = 8;
        soe2SUN1.realDuration = 9;
        soe2SUN1.state = SoeExamState.COMPLETED;
        soe2SUN1.examinators = examinators1;
        soe2SUN1.save();

        SoeExam soe2SUN2 = new SoeExam();
        soe2SUN2.course = course2SUN;
        soe2SUN2.date = new LocalDate();
        soe2SUN2.plannifiedDuration = 8;
        soe2SUN2.state = SoeExamState.PLANNIFIED;
        soe2SUN2.examinators = examinators2;
        soe2SUN2.save();

        SoeExam soe2SUN3 = new SoeExam();
        soe2SUN3.course = course2SUN;
        soe2SUN3.date = new LocalDate();
        soe2SUN3.state = SoeExamState.WAITING;
        soe2SUN3.save();

        Logger.debug("Done.");
    }

    private static void initUsers() {

        User user75054 = new User("75054", Profile.STA);
        User user76330 = new User("76330", Profile.STA);
        User user83026 = new User("83026", Profile.STA);
        User user68621 = new User("68621", Profile.STA);
        User user78349 = new User("78349", Profile.STA);
        User user76179 = new User("76179", Profile.STA);
        User user68541 = new User("68541", Profile.STA);
        User user77014 = new User("77014", Profile.STA);
        User user60371 = new User("60371", Profile.STA);
        User user68715 = new User("68715", Profile.STA);
        User user97310 = new User("97310", Profile.STA);
        User user59260 = new User("59260", Profile.STA);
        User user74861 = new User("74861", Profile.STA);
        User userDavy = new User("", Profile.STA); //DONE
        User userTrang = new User("", Profile.STA);
        User userChris = new User("", Profile.STA);
        User user124407 = new User("124407", Profile.STA);
        User user92539 = new User("92539", Profile.STA);
        User user92839 = new User("92839", Profile.STA);
        User user129165 = new User("129165", Profile.STA);
        User user106003 = new User("106003", Profile.STA);
        User user79786 = new User("79786", Profile.STA);
        User user111555 = new User("111555", Profile.STA);
        User user90352 = new User("90352", Profile.STA);
        User user72779 = new User("72779", Profile.STA);
        User user76077 = new User("76077", Profile.STA);
        User user58954 = new User("58954", Profile.STA);
        User user68341 = new User("68341", Profile.STA);
        User user75667 = new User("75667", Profile.STA);
        User user106139 = new User("106139", Profile.STA);
        User user115732 = new User("115732", Profile.STA);
        User user128771 = new User("128771", Profile.STA);
        User user128921 = new User("128921", Profile.STA);
        User user131468 = new User("131468", Profile.STA);
        User user93645 = new User("93645", Profile.STA);
        User user68928 = new User("68928", Profile.STA);
        User user68423 = new User("68423", Profile.STA);
        User user78302 = new User("78302", Profile.STA);
        User user125516 = new User("125516", Profile.STA);
        User user95424 = new User("95424", Profile.STA);
        User user136135 = new User("136135", Profile.STA);
        User user115634 = new User("115634", Profile.STA);
        User user68470 = new User("68470", Profile.STA);
        User user75748 = new User("75748", Profile.STA);
        User user68852 = new User("68852", Profile.STA);
        User user130762 = new User("130762", Profile.STA);
        User user133827 = new User("133827", Profile.STA);
    }
}

package init;

import controllers.Users;
import models.school.Course;
import models.school.SoeExam;
import models.school.SoeExamState;
import models.school.YearCourse;
import models.user.User;
import org.joda.time.LocalDate;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() throws Exception {

        if (User.count() == 0) {
            DatabaseInitializer.initDatabaseWithTests();
        }
    }
}

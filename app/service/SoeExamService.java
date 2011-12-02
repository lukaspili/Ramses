package service;

import models.school.Course;
import models.school.SoeExam;
import models.school.SoeExamState;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class SoeExamService {

    public List<SoeExamState> getStatesList() {
        return Arrays.asList(SoeExamState.values());
    }

    public void update(SoeExam soe, Set<User> users) {
        soe.examinators = users;
        soe.save();
    }

    public SoeExam create(SoeExam soe, YearCourse course, Set<User> users) {
        soe.id = null;
        soe.examinators = users;
        soe.course = course;
        soe.save();

        return soe;
    }

    public List<YearCourse> getNotOrderedSoesForUser(User user) {

        Query query = SoeExam.em().createQuery("select s from SoeExam s " +
                "where s.id not in (select jos.id from JobOrder jo join jo.soeExams jos where jo.user = :user)");

        query.setParameter("user", user);

        return query.getResultList();
    }
}

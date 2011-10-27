package service;

import models.school.YearCourse;
import models.user.User;
import org.joda.time.LocalDate;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearCourseService extends AbstractService<YearCourse> {

    public int getCurrentYear() {

        LocalDate date = new LocalDate();

        if (date.getMonthOfYear() >= 10) {
            return date.getYear() + 1;
        } else {
            return date.getYear();
        }
    }

    public List<YearCourse> getUserCoursesForYear(User user, int year) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.professor = :user " +
                "and yc.year = :year");

        query.setParameter("user", user)
                .setParameter("year", year);

        return query.getResultList();
    }

    public List<YearCourse> getUserAndAvailables(User user) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "join fetch yc.course c " +
                "where c in (:skills) " +
                "and (yc.professor is null OR yc.professor = :user) " +
                "and yc.year = :year");

        query.setParameter("skills", user.skills)
                .setParameter("user", user)
                .setParameter("year", getCurrentYear());

        return query.getResultList();
    }

    public List<YearCourse> getAllCoursesForYear(int year) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.year = :year");

        query.setParameter("year", year);

        return query.getResultList();
    }

    public void candidate(YearCourse course, User user) {
        course.candidates.add(user);
        course.save();
    }

    public void validateCandidature(YearCourse course, User user) {
        course.candidates.remove(user);
        course.professor = user;
        course.save();
    }

    public void cancelCandidature(YearCourse course, User user) {
        course.candidates.remove(user);
        course.save();
    }
}

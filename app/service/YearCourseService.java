package service;

import helpers.YearCourseHelper;
import models.school.YearCourse;
import models.user.User;
import org.joda.time.LocalDate;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearCourseService extends AbstractService<YearCourse> {

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
                .setParameter("year", YearCourseHelper.getCurrentYear());

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

    public List<YearCourse> getNotOrderedCoursesForUser(User user) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.professor.id not in (select jo.user.id from JobOrder jo) " +
                "and yc.professor = :user");

        query.setParameter("user", user);

        return query.getResultList();
    }

    public YearCourse getNotOrderedCourse(long id) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.id not in (select c.id from JobOrder jo join jo.courses c) " +
                "and yc.id = :id");

        query.setParameter("id", id);

        try {
            return (YearCourse) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
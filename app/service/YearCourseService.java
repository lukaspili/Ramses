package service;

import exceptions.CoreException;
import helpers.YearCourseHelper;
import models.school.Course;
import models.school.Promotion;
import models.school.YearCourse;
import models.school.YearPromotion;
import models.user.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearCourseService extends AbstractService<YearCourse> {

    public List<YearCourse> getUserCoursesForYear(User user, int year) {

//        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
//                "where yc.id in (select ycp.yearCourse.id from YearCoursesProfessors ycp where ycp.professor = :user) " +
//                "and yc.year = :year");
//
//        query.setParameter("user", user)
//                .setParameter("year", year);
//
//        return query.getResultList();

        return new ArrayList<YearCourse>();
    }

    public List<YearCourse> getAvailableCoursesForUser(User user) {

        if (user.skills.isEmpty()) {
            return new ArrayList<YearCourse>();
        }

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
                "join yc.course c " +
                "where yc.year = :year " +
                "order by c.code");

        query.setParameter("year", year);

        return query.getResultList();
    }

    public List<YearCourse> getYearCoursesForYear(int year) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "join yc.course c " +
                "where yc.year = :year " +
                "order by yc.course.code");

        query.setParameter("year", year);

        return query.getResultList();
    }

    public List<YearCourse> getNotOrderedCoursesForUser(User user) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.id not in (select joc.id from JobOrder jo join jo.courses joc where jo.user = :user) " +
                "and yc.professor = :user and yc.duration != null and yc.startDate != null and yc.endDate != null");

        query.setParameter("user", user);

        return query.getResultList();
    }

    public YearCourse getNotOrderedCourseForUser(long id, User user) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.id not in (select c.id from JobOrder jo join jo.courses c where jo.user = :user) " +
                "and yc.id = :id and yc.professor = :user");

        query.setParameter("id", id);
        query.setParameter("user", user);

        try {
            return (YearCourse) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void update(YearCourse yearCourse) throws CoreException {

        YearCourse fromDb = YearCourse.findById(yearCourse.id);

        if (null == fromDb) {
            throw new CoreException().type(CoreException.Type.NULL);
        }

//        if (!isEditable(fromDb)) {
//            throw new CoreException().type(CoreException.Type.REJECTED);
//        }

        fromDb.duration = yearCourse.duration;
//        fromDb.startDate = yearCourse.startDate;
//        fromDb.endDate = yearCourse.endDate;
        fromDb.save();
    }

//    public boolean isEditable(YearCourse yearCourse) {
//
//        if (!yearCourse.orders.isEmpty() || yearCourse.year < YearCourseHelper.getCurrentYear()) {
//            return false;
//        }
//
//        return true;
//    }

    public List<YearCourse> getYearCoursesByIdsAndPromotion(Collection<Long> ids, Promotion promotion) {

        if (null == ids || ids.isEmpty()) {
            return new ArrayList<YearCourse>();
        }

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.id in :ids and yc.course.promotion = :promotion");

        query.setParameter("ids", ids);
        query.setParameter("promotion", promotion);

        return query.getResultList();
    }

    public YearCourse create(YearCourse yearCourse, int year) {

        checkNotNull(yearCourse);

        YearCourse yearCourseToSave = new YearCourse();
        yearCourseToSave.name = yearCourse.name;
        yearCourseToSave.duration = yearCourse.duration;
        yearCourseToSave.course = yearCourse.course;
        yearCourseToSave.type = yearCourse.type;
        yearCourseToSave.year = year;
        yearCourseToSave.save();

        List<YearPromotion> yearPromotions = new YearPromotionService().getYearPromotionsByPromotionAndYear(yearCourseToSave.course.promotion, year);

        PromotionCourseService promotionCourseService = new PromotionCourseService();

        for (YearPromotion yearPromotion : yearPromotions) {
            promotionCourseService.create(yearCourseToSave, yearPromotion);
        }

        return yearCourseToSave;
    }

    public List<YearCourse> getYearCoursesByCourseAndYear(Course course, int year) {

        Query query = YearCourse.em().createQuery("select yc from YearCourse yc " +
                "where yc.course = :course and yc.year = :year");

        query.setParameter("course", course)
                .setParameter("year", year);

        return query.getResultList();
    }
}

package service;

import models.contracts.JobOrder;
import models.school.Prestation;
import models.school.PromotionCourse;
import models.user.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class PrestationService {

    public Prestation getNotOrderedRcpByIdAndUser(long id, User user) {

        Query query = Prestation.em().createQuery("select p from Prestation p " +
                "join p.realCourse pc join pc.yearCourse yc join yc.course c " +
                "where p.jobOrder = null and pc.id = :id and p.professor = :professor");

        query.setParameter("id", id);
        query.setParameter("professor", user);

        try {
            return (Prestation) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Prestation getPrestationByProfessorAndPromotion(User professor, PromotionCourse promotionCourse) {

        Query query = Prestation.em().createQuery("select p from Prestation p " +
                "where p.realCourse = :promotionCourse and p.professor = :professor");

        query.setParameter("promotionCourse", promotionCourse)
                .setParameter("professor", professor);

        try {
            return (Prestation) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Prestation create(User professor, PromotionCourse promotionCourse, long hours) {

        Prestation prestation = Prestation.find("byProfessorAndRealCourse", professor, promotionCourse).first();

        if (null != prestation) {
            prestation.realCourse.professorsHours -= prestation.hours;
        } else {
            prestation = new Prestation();
            prestation.professor = professor;
            prestation.realCourse = promotionCourse;
        }

        prestation.hours = hours;
        prestation.save();

        prestation.realCourse.professorsHours += prestation.hours;
        prestation.realCourse.save();

        return prestation;
    }

    public List<Prestation> getPrestationsByPromotionCourse(PromotionCourse promotionCourse) {

        Query query = Prestation.em().createQuery("select p from Prestation p " +
                "join p.realCourse pc join p.professor pr " +
                "where p.realCourse = :promotionCourse");

        query.setParameter("promotionCourse", promotionCourse);

        return query.getResultList();
    }

    public List<Prestation> getNotOrderedPrestationsByUser(User user) {

        Query query = Prestation.em().createQuery("select p from Prestation p " +
                "join p.realCourse pc join pc.yearCourse yc join yc.course c " +
                "where p.jobOrder = null and p.professor = :professor and p.hours > 0");

        query.setParameter("professor", user);

        return query.getResultList();
    }

    public void addJobOrderToPrestations(JobOrder jobOrder, List<Prestation> prestations) {

        if (null == prestations || prestations.isEmpty()) {
            return;
        }

        Query query = Prestation.em().createQuery("update Prestation p set p.jobOrder = :jobOrder where p in :prestations");
        query.setParameter("jobOrder", jobOrder)
                .setParameter("prestations", prestations);

        query.executeUpdate();
    }

    public List<Prestation> getPrestationsByProfessorAndYear(User professor, int year) {

        Query query = Prestation.em().createQuery("select p from Prestation p " +
                "join p.realCourse pc join pc.yearCourse yc join yc.course c " +
                "where p.professor = :professor and yc.year = :year");

        query.setParameter("professor", professor)
                .setParameter("year", year);

        return query.getResultList();
    }
}

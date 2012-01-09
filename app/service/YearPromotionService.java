package service;

import models.school.YearCourse;
import models.school.YearPromotion;

import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class YearPromotionService {

    public List<YearPromotion> getYearPromotionsForYear(int year) {

        Query query = YearPromotion.em().createQuery("select yp from YearPromotion yp " +
                "where yp.year = :year " +
                "order by yp.name");

        query.setParameter("year", year);

        return query.getResultList();
    }

    public YearPromotion create(YearPromotion yearPromotion, Set<YearCourse> yearCourses, int year) {

        YearPromotion promotion = new YearPromotion();

        promotion.name = yearPromotion.name;
        promotion.studentsNumber = yearPromotion.studentsNumber;
        promotion.promotion = yearPromotion.promotion;

        promotion.yearCourses = yearCourses;
        promotion.year = year;

        promotion.save();

        return promotion;
    }
}

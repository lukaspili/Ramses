package service;

import models.school.PromotionCourse;
import models.school.YearCourse;
import models.school.YearPromotion;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class PromotionCourseService {

    public PromotionCourse create(YearCourse yearCourse, YearPromotion yearPromotion) {

        PromotionCourse promotionCourse = new PromotionCourse();
        promotionCourse.yearCourse = yearCourse;
        promotionCourse.yearPromotion = yearPromotion;
        promotionCourse.save();

        return promotionCourse;
    }

    public PromotionCourse update(PromotionCourse promotionCourse) {

        PromotionCourse promotionCourseToUpdate = PromotionCourse.findById(promotionCourse.id);

        if (null == promotionCourseToUpdate) {
            return null;
        }

        promotionCourseToUpdate.startDate = promotionCourse.startDate;
        promotionCourseToUpdate.endDate = promotionCourse.endDate;
        promotionCourseToUpdate.save();

        return promotionCourseToUpdate;
    }
}

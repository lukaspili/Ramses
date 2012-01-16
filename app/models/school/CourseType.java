package models.school;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class CourseType extends Model {

    public String name;

    public float priceHigh;

    public float priceLow;

    public float getPriceForYearPromotion(YearPromotion yearPromotion) {

        if (yearPromotion.studentsNumber > 75) {
            return priceHigh;
        }

        return priceLow;

    }
}

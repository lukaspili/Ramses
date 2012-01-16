package models.school;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Join table between yearcourse and yearpromotion
 *
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@Entity
@Table(name = "realcourse")
public class PromotionCourse extends Model {

    public long professorsHours;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate startDate;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate endDate;

    @ManyToOne
    public YearCourse yearCourse;

    @ManyToOne
    public YearPromotion yearPromotion;

    @OneToMany(mappedBy = "realCourse")
    public List<Prestation> prestations;

    public boolean isPlannified() {
        return null != startDate && null != endDate;
    }

    public long getNotPlannifiedHours() {
        return yearCourse.duration - professorsHours;
    }

    /**
     * Return the price for the year course based on the nb of hours and the price for 1 hour defined by the course type
     * Be careful because this method requires a JOIN on the course type entity
     *
     * @return the price of the year course
     */
//    public float getTotal() {
//        return yearCourse * type.price;
//    }
}
package models.school;

import models.contracts.JobOrder;
import models.user.User;
import play.db.jpa.GenericModel;

import javax.persistence.*;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@Entity
@Table(name = "realcourses_professors")
public class Prestation extends GenericModel {

    @Id
    @ManyToOne
    public User professor;

    @Id
    @ManyToOne
    public PromotionCourse realCourse;

    @Column(name = "duration")
    public long hours;

    @ManyToOne
    public JobOrder jobOrder;

    public float getTotal() {
        return hours * realCourse.yearCourse.type.getPriceForYearPromotion(realCourse.yearPromotion);
    }


}

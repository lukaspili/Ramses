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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Prestation that = (Prestation) o;

        if (hours != that.hours) return false;
        if (jobOrder != null ? !jobOrder.equals(that.jobOrder) : that.jobOrder != null) return false;
        if (professor != null ? !professor.equals(that.professor) : that.professor != null) return false;
        if (realCourse != null ? !realCourse.equals(that.realCourse) : that.realCourse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (professor != null ? professor.hashCode() : 0);
        result = 31 * result + (realCourse != null ? realCourse.hashCode() : 0);
        result = 31 * result + (int) (hours ^ (hours >>> 32));
        result = 31 * result + (jobOrder != null ? jobOrder.hashCode() : 0);
        return result;
    }
}

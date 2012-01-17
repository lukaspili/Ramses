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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PromotionCourse that = (PromotionCourse) o;

        if (professorsHours != that.professorsHours) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (yearCourse != null ? !yearCourse.equals(that.yearCourse) : that.yearCourse != null) return false;
        if (yearPromotion != null ? !yearPromotion.equals(that.yearPromotion) : that.yearPromotion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (professorsHours ^ (professorsHours >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (yearCourse != null ? yearCourse.hashCode() : 0);
        result = 31 * result + (yearPromotion != null ? yearPromotion.hashCode() : 0);
        return result;
    }
}
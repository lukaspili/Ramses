package models.contracts;

import models.school.YearCourse;
import models.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@Entity
public class SpecificPrestation extends Model {

    public long hours;

    public String title;

    public float price;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate date;

    @ManyToOne
    public User user;

    @ManyToOne
    public YearCourse yearCourse;

    @ManyToOne
    public JobOrder jobOrder;

    public float getTotal() {
        return hours * price;
    }
}

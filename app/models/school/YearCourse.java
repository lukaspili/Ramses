package models.school;

import models.contracts.JobOrder;
import models.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class YearCourse extends Model {

    public int year;

    public int duration;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate startDate;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate endDate;

    @ManyToOne
    public Course course;

    @ManyToOne
    public User professor;

    @ManyToMany
    public Set<User> candidates;

    @OneToMany(mappedBy = "course")
    public Set<SoeExam> soeExams;

    @ManyToMany(mappedBy = "courses")
    public Set<JobOrder> orders;

    public boolean hasProfessor() {
        return null != professor;
    }

    public float getTotal() {
        return duration * course.type.price;
    }

    public boolean isPlannified() {
        return null != startDate && null != endDate && duration != 0;
    }
}

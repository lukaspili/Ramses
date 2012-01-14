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
import java.util.List;
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
    public CourseType type;

    @ManyToOne
    public Course course;

    @ManyToOne
    public User professor;

    @ManyToMany
    public List<User> candidates;

    @OneToMany(mappedBy = "course")
    public List<SoeExam> soeExams;

    @ManyToMany(mappedBy = "courses")
    public List<JobOrder> orders;

    @ManyToMany(mappedBy = "yearCourses")
    public List<YearPromotion> yearPromotions;

    public boolean hasProfessor() {
        return null != professor;
    }

    /**
     * Return the price for the year course based on the nb of hours and the price for 1 hour defined by the course type
     * Be careful because this method requires a JOIN on the course type entity
     *
     * @return the price of the year course
     */
    public float getTotal() {
        return duration * type.price;
    }

    public boolean isPlannified() {
        return null != startDate && null != endDate && duration != 0;
    }
}

package models.school;

import models.user.User;
import org.apache.commons.lang3.StringUtils;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class YearCourse extends Model {

    public String name;

    public int year;

    public int duration;

    @ManyToOne
    public CourseType type;

    @ManyToOne
    public Course course;

    @OneToMany(mappedBy = "yearCourse")
    public List<PromotionCourse> promotionCourses;

    @OneToMany(mappedBy = "course")
    public List<SoeExam> soeExams;

//    @ManyToMany(mappedBy = "courses")
//    public List<JobOrder> orders;

//    @ManyToMany
//    public List<YearPromotion> yearPromotions;

    public String getFullName() {
        return course.getFullName() + (StringUtils.isNotBlank(name) ? (" - " + name) : "");
    }
}

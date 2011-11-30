package models.school;

import models.user.User;
import org.hibernate.annotations.*;
import play.db.jpa.Model;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Course extends Model {

    public String name;
    public String code;

    @ManyToOne
    public CourseType type;

    @Enumerated(EnumType.STRING)
    public Promotion promotion;

    @ManyToMany(mappedBy = "skills")
    public Set<User> users;

    @OneToMany(mappedBy = "course")
    public Set<YearCourse> yearCourses;

    public String getFullName() {
        return code + " - " + name;
    }
}

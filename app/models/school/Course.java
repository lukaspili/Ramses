package models.school;

import models.user.User;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Course extends Model {

    public String name;

    public String code;

    @Enumerated(EnumType.STRING)
    public Promotion promotion;

    @ManyToMany(mappedBy = "skills")
    public List<User> users;

    @ManyToMany
    public List<User> candidates;

    @OneToMany(mappedBy = "course")
    public List<YearCourse> yearCourses;

    public Course() {
    }

    public Course(String code, String name, Promotion promotion) {
        this.promotion = promotion;
        this.code = code;
        this.name = name;
    }

    public String getFullName() {
        return getFullCode() + " - " + name;
    }

    public String getFullCode() {
        return promotion.level + code;
    }
}

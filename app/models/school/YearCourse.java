package models.school;

import models.user.User;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class YearCourse extends Model {

    public int year;

    @ManyToOne
    public Course course;

    @ManyToOne
    public User professor;

    @ManyToMany
    public Set<User> candidates;

    public boolean hasProfessor() {
        return null != professor;
    }
}

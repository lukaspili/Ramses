package models.school;

import models.user.User;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Course extends Model {

    public String name;
    public String code;
    public int duration;

    @ManyToOne
    public CourseType type;

    @Enumerated(EnumType.STRING)
    public Promotion promotion;

    @ManyToMany
    public List<User> users = new ArrayList<User>();

    public String getFullName() {
        return code + " - " + name;
    }
}

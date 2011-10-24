package models.school;

import models.user.User;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class YearCourse extends Model {

    public Course course;
    public User professor;
    public int year;
}

package models.school;

import models.user.User;
import play.db.jpa.GenericModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@Entity
@Table(name = "yearcourses_professors")
public class YearCoursesProfessors extends GenericModel {

    public int duration;

    @Id
    @ManyToOne
    public User professor;

    @Id
    @ManyToOne
    public YearCourse yearCourse;
}

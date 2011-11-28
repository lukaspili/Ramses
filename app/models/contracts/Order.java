package models.contracts;

import models.school.YearCourse;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Order extends Model {

    public String path;

    @ManyToOne
    public Contract contract;

    @OneToOne
    public YearCourse yearCourse;
}

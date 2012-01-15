package models.school;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class CourseType extends Model {

    public String name;

    public float price;

    @Enumerated(EnumType.STRING)
    public Level level;

    public CourseType() {
    }

    public CourseType(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public enum Level {
        LEVEL1, LEVEL2, LEVEL3
    }
}

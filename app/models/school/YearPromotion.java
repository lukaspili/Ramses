package models.school;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class YearPromotion extends Model {

    public String name;

    public int studentsNumber;

    public int year;

    @Enumerated(EnumType.STRING)
    public Promotion promotion;

    @ManyToMany
    public Set<YearCourse> yearCourses;

    public String getFullName() {
        return promotion.getLabel() + name;
    }
}

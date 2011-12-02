package models.school;

import models.user.User;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class SoeExam extends Model {

    public static final float PRICE = 25;

    public int plannifiedDuration;

    public int realDuration;

    public LocalDate date;

    @Enumerated(EnumType.STRING)
    public SoeExamState state = SoeExamState.WAITING;

    @ManyToOne
    public YearCourse course;

    @ManyToMany
    public Set<User> examinators;

    public float getTotal() {
        return plannifiedDuration * SoeExam.PRICE;
    }
}

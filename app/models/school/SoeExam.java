package models.school;

import models.user.User;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class SoeExam extends Model {

    public static final float PRICE = 20;

    public int plannifiedDuration;

    public int realDuration;

    public LocalDate date;

    @Enumerated(EnumType.STRING)
    public SoeExamState state = SoeExamState.WAITING;

    @ManyToOne
    public YearCourse course;

    @ManyToMany
    public List<User> examinators;

    public float getTotal() {
        return plannifiedDuration * SoeExam.PRICE;
    }
}

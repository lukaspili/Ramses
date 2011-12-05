package models.contracts;

import models.school.SoeExam;
import models.school.YearCourse;
import models.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import s3.storage.S3Blob;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class JobOrder extends Model {

    public float total;

    public S3Blob pdf;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    public JobOrderState state = JobOrderState.CREATED;

    @ManyToOne
    public Contract contract;

    @ManyToOne
    public User user;

    @ManyToMany
    public Set<SoeExam> soeExams;

    @ManyToMany
    public Set<YearCourse> courses;
}

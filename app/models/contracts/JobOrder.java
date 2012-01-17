package models.contracts;

import models.school.Prestation;
import models.school.SoeExam;
import models.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Model;
import plugin.s3.model.S3Blob;
import plugin.s3.model.impl.S3RealBlob;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class JobOrder extends Model {

    public long jobOrderNumber;

    public float total;

    public S3RealBlob pdf;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    public JobOrderState state = JobOrderState.CREATED;

    @ManyToOne
    public Contract contract;

    @ManyToOne
    public User user;

    @ManyToMany
    public List<SoeExam> soeExams;

    @OneToMany(mappedBy = "jobOrder")
    public List<Prestation> realCoursesProfessors;

    /**
     * Add 0 before if the order id is in 1 -> 99, in case to have id number like 001, 002, 010, etc...
     */
    public String getFormattedNumber() {

        if (jobOrderNumber < 10) {
            return "00" + jobOrderNumber;
        } else if (id < 100) {
            return "0" + jobOrderNumber;
        }

        return String.valueOf(jobOrderNumber);
    }
}

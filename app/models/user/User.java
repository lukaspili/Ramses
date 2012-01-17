package models.user;

import models.contracts.Contract;
import models.contracts.JobOrder;
import models.school.Course;
import models.school.Prestation;
import models.school.SoeExam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import play.data.validation.CheckWith;
import play.db.jpa.Model;
import validation.check.NumericCheck;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
@Table(name = "users")
public class User extends Model {

    public long staNumber;

    @CheckWith(NumericCheck.class)
    public String idBooster;

    public String firstName;

    public String lastName;

    public String password;

    public String street;

    public String postalCode;

    public String city;

    public String siret;

    public String rcs;

    public boolean active;

    public boolean desactivated;

    @Enumerated(EnumType.STRING)
    public Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    public Contract contract;

    @ManyToMany
    public List<Course> skills;

    @ManyToMany(mappedBy = "examinators")
    public List<SoeExam> soeExams;

    @OneToMany(mappedBy = "professor")
    public List<Prestation> realCourses;

    @OneToMany(mappedBy = "user")
    public List<JobOrder> orders;

    public User() {
    }

    public User(String idBooster, Profile profile) {
        this.idBooster = idBooster;
        this.profile = profile;
    }
    
    public String getIdBoosterAndFullname() {
        return idBooster + " - " + getFullName();
    }

    public String getOfficialFullName() {

        if (StringUtils.isBlank(lastName)) {
            return null;
        }

        return lastName + " " + firstName;
    }

    public String getFullName() {

        if (StringUtils.isBlank(lastName)) {
            return null;
        }

        return firstName + " " + lastName;
    }

    public String getEmail() {
        return idBooster + "@supinfo.com";
    }

    public boolean hasContract() {
        return null != contract;
    }

    /**
     * Add 0 before if the user id is in 1 -> 9, in case to have id number like 01, 02, 03, etc...
     */
    public String getFormattedStaNumber() {

        if (staNumber < 10) {
            return "0" + staNumber;
        }

        return String.valueOf(staNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("staNumber", staNumber)
                .append("idBooster", idBooster)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("password", password)
                .append("street", street)
                .append("postalCode", postalCode)
                .append("city", city)
                .append("siret", siret)
                .append("rcs", rcs)
                .append("active", active)
                .append("desactivated", desactivated)
                .append("profile", profile)
                .appendSuper(super.toString())
                .toString();
    }
}

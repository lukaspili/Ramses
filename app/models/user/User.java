package models.user;

import models.school.Course;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import play.data.validation.CheckWith;
import play.db.jpa.Model;
import validation.check.NumericCheck;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
@Table(name = "users")
public class User extends Model {

    @CheckWith(NumericCheck.class)
    public String idBooster;

    public String firstName;

    public String lastName;

    public String password;

    public String street;

    public String postalCode;

    public String city;

    public String siret;

    public boolean active;

    @Enumerated(EnumType.STRING)
    public Profile profile;

    @ManyToMany(mappedBy = "users")
    public List<Course> courses = new ArrayList<Course>();

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idBooster", idBooster)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("password", password)
                .append("siret", siret)
                .append("active", active)
                .append("profile", profile)
                .appendSuper(super.toString())
                .toString();
    }
}

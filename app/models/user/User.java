package models.user;

import models.contracts.ContractCadre;
import models.school.Course;
import models.school.YearCourse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import play.data.validation.CheckWith;
import play.db.jpa.Model;
import validation.check.NumericCheck;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @OneToOne
    public ContractCadre contractCadre;

    @ManyToMany
    public Set<Course> skills;

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

    public boolean hasContractCadre() {
        return null != contractCadre;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (active != user.active) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (idBooster != null ? !idBooster.equals(user.idBooster) : user.idBooster != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (postalCode != null ? !postalCode.equals(user.postalCode) : user.postalCode != null) return false;
        if (profile != user.profile) return false;
        if (siret != null ? !siret.equals(user.siret) : user.siret != null) return false;
        if (street != null ? !street.equals(user.street) : user.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (idBooster != null ? idBooster.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (siret != null ? siret.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
    }
}

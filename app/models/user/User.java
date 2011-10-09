package models.user;

import play.data.validation.CheckWith;
import play.db.jpa.Model;
import validation.check.NumericCheck;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class User extends Model {

    @CheckWith(NumericCheck.class)
    public String idBooster;

    public String firstName;

    public String lastName;

    public String password;

    public String siret;

    @Enumerated(EnumType.STRING)
    public Profile profile;
}

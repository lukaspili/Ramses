package models.contracts;

import models.user.User;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class ContractCadre extends Model {

    public int year;

    public String path;

    @OneToOne(mappedBy = "contractCadre")
    public User user;
}

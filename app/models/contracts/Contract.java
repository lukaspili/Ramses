package models.contracts;

import models.user.User;
import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Contract extends Model {

    public int year;

    public Blob pdf;

    @Enumerated(EnumType.STRING)
    public ContractState state = ContractState.CREATED;

    @OneToOne(mappedBy = "contract")
    public User user;
}

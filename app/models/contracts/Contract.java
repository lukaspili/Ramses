package models.contracts;

import models.user.User;
import play.db.jpa.Model;
import s3.storage.S3Blob;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Contract extends Model {

    public int year;

    public S3Blob pdf;

    @Enumerated(EnumType.STRING)
    public ContractState state = ContractState.CREATED;

    @OneToOne(mappedBy = "contract")
    public User user;
}

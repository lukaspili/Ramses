package s3.storage;

import org.hibernate.usertype.UserType;
import play.db.Model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface S3BlobInterface extends Model.BinaryField, UserType {

    void delete();
}

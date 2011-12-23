package s3.storage;

import org.hibernate.usertype.UserType;
import play.db.Model;
import play.db.jpa.Blob;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class S3DevBlob extends Blob implements S3BlobInterface {

    @Override
    public void delete() {
        getFile().delete();
    }
}

package plugin.s3.model.impl;

import play.db.jpa.Blob;
import plugin.s3.model.S3BlobInterface;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class S3MockBlob extends Blob implements S3BlobInterface {

    @Override
    public void delete() {
        getFile().delete();
    }
}

package plugin.s3.model.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import play.Play;
import play.libs.Codec;
import plugin.s3.model.S3BlobInterface;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class S3RealBlob implements S3BlobInterface {

    public static String s3Bucket;
    public static AmazonS3 s3Client;

    private String bucket;
    private String key;

    public S3RealBlob() {
    }

    private S3RealBlob(String bucket, String s3Key) {
        this.bucket = bucket;
        this.key = s3Key;
    }

    @Override
    public InputStream get() {
        S3Object s3Object = s3Client.getObject(bucket, key);
        return s3Object.getObjectContent();
    }

    @Override
    public void set(InputStream is, String type) {

        this.bucket = s3Bucket;
        this.key = Play.configuration.getProperty("application.mode") + "/" + Codec.UUID();

        ObjectMetadata om = new ObjectMetadata();
        om.setContentType(type);

        s3Client.putObject(bucket, key, is, om);
    }

    @Override
    public long length() {
        ObjectMetadata om = s3Client.getObjectMetadata(bucket, key);
        return om.getContentLength();
    }

    @Override
    public String type() {
        ObjectMetadata om = s3Client.getObjectMetadata(bucket, key);
        return om.getContentType();
    }

    @Override
    public boolean exists() {
        try {
            ObjectMetadata om = s3Client.getObjectMetadata(bucket, key);
            return om != null;
        } catch (AmazonS3Exception e) {
            return false;
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return S3RealBlob.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == null ? false : o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object o) throws HibernateException, SQLException {
        String val = (String) StringType.INSTANCE.nullSafeGet(rs, names[0]);
        if (val == null || val.length() == 0 || !val.contains("|")) {
            return new S3RealBlob();
        }
        return new S3RealBlob(val.split("[|]")[0], val.split("[|]")[1]);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i) throws HibernateException, SQLException {
        if (o != null) {
            ps.setString(i, ((S3RealBlob) o).bucket + "|" + ((S3RealBlob) o).key);
        } else {
            ps.setNull(i, Types.VARCHAR);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        }
        return new S3RealBlob(this.bucket, this.key);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    public void delete() {
        s3Client.deleteObject(bucket, key);
    }

    public Serializable disassemble(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object assemble(Serializable srlzbl, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

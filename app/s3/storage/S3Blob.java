package s3.storage;

import org.hibernate.HibernateException;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class S3Blob implements S3BlobInterface {

    public static Type type = Type.DEV;

    private S3ProdBlob prodBlob;
    private S3DevBlob devBlob;

    public S3Blob() {
        prodBlob = new S3ProdBlob();
        devBlob = new S3DevBlob();
    }

    private S3BlobInterface getCurrentBlob() {

        if (type == Type.DEV) {
            return getDevBlob();
        } else {
            return getProdBlob();
        }
    }

    private S3ProdBlob getProdBlob() {

        if (null == prodBlob) {
            prodBlob = new S3ProdBlob();
        }

        return prodBlob;
    }

    private S3DevBlob getDevBlob() {

        if (null == devBlob) {
            devBlob = new S3DevBlob();
        }

        return devBlob;
    }

    @Override
    public void delete() {
        getCurrentBlob().delete();
    }

    @Override
    public InputStream get() {
        return getCurrentBlob().get();
    }

    @Override
    public void set(InputStream is, String type) {
        getCurrentBlob().set(is, type);
    }

    @Override
    public long length() {
        return getCurrentBlob().length();
    }

    @Override
    public String type() {
        return getCurrentBlob().type();
    }

    @Override
    public boolean exists() {
        return getCurrentBlob().exists();
    }

    @Override
    public int[] sqlTypes() {
        return getCurrentBlob().sqlTypes();
    }

    @Override
    public Class returnedClass() {
        return getCurrentBlob().returnedClass();
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return getCurrentBlob().equals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return getCurrentBlob().hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o) throws HibernateException, SQLException {
        return getCurrentBlob().nullSafeGet(resultSet, strings, getCurrentBlob());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException, SQLException {
        getCurrentBlob().nullSafeSet(preparedStatement, getCurrentBlob(), i);
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return getCurrentBlob().deepCopy(o);
    }

    @Override
    public boolean isMutable() {
        return getCurrentBlob().isMutable();
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return getCurrentBlob().disassemble(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return getCurrentBlob().assemble(serializable, o);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return getCurrentBlob().replace(o, o1, o2);
    }

    public enum Type {
        DEV, PROD
    }
}

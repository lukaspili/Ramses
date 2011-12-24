package plugin.s3.model;

import org.hibernate.HibernateException;
import plugin.s3.model.impl.S3MockBlob;
import plugin.s3.model.impl.S3RealBlob;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class S3Blob implements S3BlobInterface {

    public static Mode mode = Mode.MOCK;

    private S3RealBlob realBlob;
    private S3MockBlob mockBlob;

    public S3Blob() {
        realBlob = new S3RealBlob();
        mockBlob = new S3MockBlob();
    }

    private S3BlobInterface getCurrentBlob() {

        if (mode == Mode.MOCK) {
            return getMockBlob();
        } else {
            return getRealBlob();
        }
    }

    private S3RealBlob getRealBlob() {

        if (null == realBlob) {
            realBlob = new S3RealBlob();
        }

        return realBlob;
    }

    private S3MockBlob getMockBlob() {

        if (null == mockBlob) {
            mockBlob = new S3MockBlob();
        }

        return mockBlob;
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

    public enum Mode {
        MOCK, REAL
    }
}

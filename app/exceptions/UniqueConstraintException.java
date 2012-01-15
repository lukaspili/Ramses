package exceptions;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class UniqueConstraintException extends Exception {

    public UniqueConstraintException() {
        super();
    }

    public UniqueConstraintException(String s) {
        super(s);
    }

    public UniqueConstraintException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UniqueConstraintException(Throwable throwable) {
        super(throwable);
    }
}

package exceptions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CoreException extends RuntimeException {

    public CoreException() {
        super();
    }

    public CoreException(String s) {
        super(s);
    }

    public CoreException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CoreException(Throwable throwable) {
        super(throwable);
    }
}

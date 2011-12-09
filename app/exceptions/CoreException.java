package exceptions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CoreException extends RuntimeException {

    private Type type = Type.NOT_SPECIFIED;

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

    public CoreException type(Type type) {
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {
        NOT_SPECIFIED, UNIQUE_CONSTRAINT_VIOLATION, REJECTED, NULL
    }
}

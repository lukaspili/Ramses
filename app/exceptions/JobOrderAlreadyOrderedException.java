package exceptions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JobOrderAlreadyOrderedException extends RuntimeException {

    public JobOrderAlreadyOrderedException() {
    }

    public JobOrderAlreadyOrderedException(String s) {
        super(s);
    }

    public JobOrderAlreadyOrderedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JobOrderAlreadyOrderedException(Throwable throwable) {
        super(throwable);
    }
}

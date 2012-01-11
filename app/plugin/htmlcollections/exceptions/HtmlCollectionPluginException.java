package plugin.htmlcollections.exceptions;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class HtmlCollectionPluginException extends RuntimeException {

    public HtmlCollectionPluginException() {
        super();
    }

    public HtmlCollectionPluginException(String s) {
        super(s);
    }

    public HtmlCollectionPluginException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HtmlCollectionPluginException(Throwable throwable) {
        super(throwable);
    }
}

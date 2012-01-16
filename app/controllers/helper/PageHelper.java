package controllers.helper;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class PageHelper extends AbstractHelper {

    private static final String TAG = "page_title";

    public void addActionTitle(String... args) {
        title(request().controller.toLowerCase() + "." + request().actionMethod, args);
    }

    public void addControllerTitle() {
        title(request().controller.toLowerCase());
    }

    public void addTitleWithController(String title, String... args) {
        title(request().controller.toLowerCase() + "." + title, args);
    }

    public void title(String title, String... args) {
        renderArgs().put(TAG, Messages.get(title, args));
    }

    public void directTitle(String title) {
        renderArgs().put(TAG, title);
    }
}

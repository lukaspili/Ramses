package controllers.helper;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class PageHelper extends AbstractHelper {

    private static final String TAG = "page_title";

    public void addActionTitle() {
        title(request().controller.toLowerCase() + "." + request().actionMethod);
    }

    public void title(String title) {
        renderArgs().put(TAG, Messages.get(title));
    }

    public void directTitle(String title) {
        renderArgs().put(TAG, title);
    }
}

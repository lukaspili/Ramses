package controllers.helper;

import play.i18n.Messages;
import play.mvc.Scope;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class PageHelper {

    private String pageName;
    private Scope.RenderArgs renderArgs;

    public PageHelper(String pageName, Scope.RenderArgs renderArgs) {
        this.pageName = pageName;
        this.renderArgs = renderArgs;
    }

    public void title(String... titles) {

        String title = Messages.get(pageName);

        for (String t : titles) {
            title += " - " + Messages.get(pageName + "." + t);
        }

        renderArgs.put("page_title", title);
    }
}

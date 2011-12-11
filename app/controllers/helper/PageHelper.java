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

        renderArgs.put("page_title", Messages.get(pageName));
    }

    public void title(String... titles) {

        String title = String.valueOf(renderArgs.get("page_title"));

        for (String t : titles) {
            title += " - " + Messages.get(pageName + "." + t);
        }

        renderArgs.put("page_title", title);
    }

    public void uniqueTitle(String title) {
        renderArgs.put("page_title", Messages.get(title));
    }
}

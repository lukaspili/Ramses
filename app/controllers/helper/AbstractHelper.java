package controllers.helper;

import play.mvc.Http;
import play.mvc.Scope;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public abstract class AbstractHelper {

    private Scope.RenderArgs renderArgs;
    private Http.Request request;

    protected Scope.RenderArgs renderArgs() {

        if (null == renderArgs) {
            renderArgs = Scope.RenderArgs.current();
        }

        return renderArgs;
    }

    protected Http.Request request() {

        if (null == request) {
            request = Http.Request.current();
        }

        return request;
    }
}

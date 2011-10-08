package controllers;

import play.mvc.Controller;
import play.mvc.With;
import validation.EnhancedValidator;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@With({Application.class, Auth.class})
public abstract class AbstractController extends Controller {

    protected static EnhancedValidator validator() {
        return new EnhancedValidator(validation, params);
    }

    protected static void flashInfo(String message) {
        flash.put("info", message);
    }

}

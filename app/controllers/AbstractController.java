package controllers;

import play.mvc.Controller;
import validation.EnhancedValidator;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractController extends Controller {

    protected static EnhancedValidator validator() {
        return new EnhancedValidator(validation, params);
    }

    protected static void flashInfo(String message) {
        flash.put("info", message);
    }

}

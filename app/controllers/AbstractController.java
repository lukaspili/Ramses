package controllers;

import play.mvc.Controller;
import play.mvc.Util;
import validation.EnhancedValidator;

import javax.validation.Valid;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractController extends Controller {

    protected static EnhancedValidator validator() {
        return new EnhancedValidator(validation, renderArgs);
    }

    protected static void flashInfo(String message) {
        flash.put("info", message);
    }

}

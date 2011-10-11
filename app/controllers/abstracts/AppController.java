package controllers.abstracts;

import controllers.Application;
import controllers.filters.UserFirstLoginFilter;
import controllers.security.Auth;
import play.mvc.With;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@With({Application.class, Auth.class, UserFirstLoginFilter.class})
public abstract class AppController extends UtilController {

}

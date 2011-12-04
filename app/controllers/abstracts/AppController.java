package controllers.abstracts;

import controllers.filters.UserFirstLoginFilter;
import controllers.filters.UserNotificationFilter;
import controllers.logics.ApplicationLogic;
import controllers.security.Auth;
import play.mvc.With;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@With({ApplicationLogic.class, Auth.class, UserFirstLoginFilter.class, UserNotificationFilter.class})
public abstract class AppController extends UtilController {

}

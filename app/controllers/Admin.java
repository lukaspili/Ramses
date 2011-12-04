package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import models.user.Profile;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class Admin extends AppController {

}

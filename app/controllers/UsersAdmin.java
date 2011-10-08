package controllers;

import models.user.Profile;
import models.user.User;
import play.mvc.With;
import service.ProfileService;
import service.UserService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@With(Auth.class)
public class UsersAdmin extends AbstractController {

    @Inject
    static UserService userService;

    @Inject
    static ProfileService profileService;

    public static void create() {
        List<Profile> profiles = profileService.getProfilesList();
        render(profiles);
    }

    public static void save(User user) throws Exception {

        if (validator().validate(user).require("idBooster", "profile").hasErrors()) {
            create();
        }

        userService.save(user);

        flashInfo("Utilisateur " + user.idBooster + " enregistré avec succès");

        Dashboard.home();
    }
}

package controllers;

import controllers.helper.PageHelper;
import models.user.Profile;
import models.user.User;
import play.mvc.Before;
import service.ProfileService;
import service.UserService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UsersAdmin extends AbstractController {

    @Inject
    private static UserService userService;

    @Inject
    private static ProfileService profileService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("usersadmin", renderArgs);
    }

    public static void create() {

        pageHelper.title("createuser");

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

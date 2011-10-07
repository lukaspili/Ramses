package controllers;

import com.google.inject.Inject;
import models.user.User;
import play.data.validation.Valid;
import play.mvc.With;
import service.UserService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@With(Auth.class)
public class UsersAdmin extends AbstractController {

    @Inject
    private static UserService userService = new UserService();

    public static void create() {
        render();
    }

    public static void save(@Valid User user) throws Exception {

        if (validator().forObject(user).require("idBooster", "profile").hasErrors()) {
            render("UsersAdmin/create.html");
        }

        userService.save(user);

        flashInfo("Utilisateur " + user.idBooster + " enregistré avec succès");

        Dashboard.home();
    }
}

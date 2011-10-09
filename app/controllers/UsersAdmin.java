package controllers;

import controllers.helper.PageHelper;
import exceptions.CoreException;
import models.user.Profile;
import models.user.User;
import play.data.validation.Required;
import play.mvc.Before;
import service.ProfileService;
import service.UserService;
import validation.EnhancedValidator;

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

        pageHelper.title("create");

        List<Profile> profiles = profileService.getProfilesList();
        render(profiles);
    }

    public static void save(User user, @Required String afterSubmit) throws Exception {

        EnhancedValidator validator = validator();

        if (validator.validate(user).require("idBooster", "profile").hasErrors()) {
            create();
        }

        try {
            userService.save(user);
        } catch (CoreException e) {

            if (e.getType().equals(CoreException.Type.UNIQUE_CONSTRAINT_VIOLATION)) {
                validator.addError("idBooster", "usersadmin.create.error.uniqueIdBooster").save();
                create();
            }

            throw e;
        }

        flashSuccess("usersadmin.save.success", user.idBooster);

        if (afterSubmit.equalsIgnoreCase("form")) {
            create();
        }

        list();
    }

    public static void list() {
        render();
    }
}

package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import exceptions.CoreException;
import models.user.Profile;
import models.user.User;
import notifiers.Mails;
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
@LoggedAccess(Profile.ADMIN)
public class UsersAdmin extends AppController {

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

            List<Profile> profiles = profileService.getProfilesList();
            render("UsersAdmin/create.html", profiles, user, afterSubmit);
        }

        try {
            userService.save(user);
        } catch (CoreException e) {

            if (e.getType().equals(CoreException.Type.UNIQUE_CONSTRAINT_VIOLATION)) {

                validator.addError("idBooster", "usersadmin.create.error.uniqueIdBooster").save();

                List<Profile> profiles = profileService.getProfilesList();
                render("UsersAdmin/create.html", profiles, user, afterSubmit);
            }

            throw e;
        }

        Mails.register(user);

        flashSuccess("usersadmin.save.success", user.idBooster);

        if (afterSubmit.equalsIgnoreCase("form")) {
            create();
        }

        list();
    }

    public static void list() {

        pageHelper.title("list");

        long inactiveCount = User.count("byActive", false);
        long staCount = User.count("byProfileAndactive", Profile.STA, true);
        long externeCount = User.count("byProfileAndactive", Profile.EXTERNE, true);
        long adminCount = User.count("byProfileAndactive", Profile.ADMIN, true);

        List<User> users = User.findAll();

        render(users, inactiveCount, staCount, externeCount, adminCount);
    }

    public static void show(long userId) {

        User user = userService.findUserWithContratAndOrders(userId);

        notFoundIfNull(user);

        String title = null;

        if (!user.active) {
            flashInfoSamePage("user.active.not_yet");
            title = "Utilisateur " + user.idBooster;
        } else {
            title = "Utilisateur " + user.getFullName();
        }

        pageHelper.uniqueTitle(title);

        render(user);
    }
}

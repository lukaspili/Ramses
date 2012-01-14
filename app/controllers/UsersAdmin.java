package controllers;

import controllers.abstracts.AppController;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import exceptions.CoreException;
import models.user.Profile;
import models.user.User;
import notifiers.Mails;
import play.data.validation.Required;
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

    public static void create() {

        pageHelper().addActionTitle();

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

        pageHelper().addActionTitle();

        long inactiveCount = User.count("byActive", false);
        long staCount = User.count("byProfileAndactive", Profile.STA, true);
        long externeCount = User.count("byProfileAndactive", Profile.EXTERNE, true);
        long adminCount = User.count("byProfileAndactive", Profile.ADMIN, true);

        List<User> users = User.find("order by staNumber").fetch();

        render(users, inactiveCount, staCount, externeCount, adminCount);
    }

    public static void show(long userId) {

        User user = userService.findUserWithContratAndOrders(userId);

        notFoundIfNull(user);

        String title = null;

        if (!user.active) {
            flashInfoSamePage("user.info.not_yet_active");
            title = "Utilisateur " + user.idBooster;
        } else {
            title = "Utilisateur " + user.getFullName();
        }

        pageHelper().addActionTitle();

        if (user.desactivated) {
            flashErrorSamePage("user.show.is_desactivated");
        }

        render(user);
    }

    public static void remove(Long id) {

        User user = User.findById(id);
        notFoundIfNull(user);

        if (user.equals(Auth.getCurrentUser())) {
            flashError("usersAdmin.remove.error.cannot_remove_yourself");
            UsersAdmin.show(id);
        }

        try {
            userService.remove(user);
        } catch (CoreException e) {

            if (e.getType() == CoreException.Type.REJECTED) {
                flashError("usersAdmin.remove.error.rejected");
                UsersAdmin.show(id);
            }

            throw e;
        }

        flashSuccess("usersAdmin.remove.succes");
        UsersAdmin.list();
    }

    public static void changeActivationState(Long id) {

        User user = User.findById(id);
        notFoundIfNull(user);

        if (user.equals(Auth.getCurrentUser()) && !user.desactivated) {
            flashError("usersAdmin.changeActivationState.error.cannot_desactivate_yourself");
            UsersAdmin.show(id);
        }

        userService.changeActivationState(user);

        if (user.desactivated) {
            flashSuccess("usersAdmin.desactivate.success");
        } else {
            flashSuccess("usersAdmin.activate.success");
        }
        UsersAdmin.show(id);
    }
}
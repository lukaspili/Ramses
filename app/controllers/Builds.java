package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import models.Build;
import models.user.Profile;
import play.mvc.Before;
import service.BuildService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Builds extends AppController {

    @Inject
    private static BuildService buildService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("builds", renderArgs);
    }

    public static void show(long buildId) {

        Build build = Build.findById(buildId);
        notFoundIfNull(build);

        pageHelper.uniqueTitle("Build " + build.getFormattedNumber());

        List<Build> builds = buildService.getBuildsShortHistory();

        render(build, builds);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void manage() {

        List<Build> builds = buildService.getBuildsShortHistory();

        render(builds);
    }

    @LoggedAccess(Profile.ADMIN)
    public static void save(Build build) {

        if (validator().validate(build).require("changelog").hasErrors()) {
            List<Build> builds = buildService.getBuildsShortHistory();
            render("Builds/manage.html", build, builds);
        }

        buildService.save(build);

        flashSuccess("builds.save.success");
        manage();
    }
}

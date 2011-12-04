package controllers.logics;

import controllers.Dashboard;
import controllers.abstracts.UtilController;
import models.Build;
import play.mvc.Before;
import service.BuildService;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ApplicationLogic extends UtilController {

    @Inject
    private static BuildService buildService;

    private static final String APPLICATION_NAME = "Eunomie";

    @Before(priority = 0)
    public static void before() {

        renderArgs.put("application_name", APPLICATION_NAME);
        renderArgs.put("globalBuild", buildService.getLatestBuild());
//
//        if (request.secure == false) {
//            request.secure = true;
//            request.port = 9443;
//            Dashboard.index();
//        }
    }
}

package controllers;

import controllers.abstracts.AppController;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import models.contracts.ContractState;
import models.contracts.JobOrder;
import models.school.Prestation;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import service.JobOrderService;
import service.PrestationService;
import service.SoeExamService;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Orders extends AppController {

    @Inject
    private static JobOrderService jobOrderService;

    @Inject
    private static YearCourseService yearCourseService;

    @Inject
    private static PrestationService prestationService;

    @Inject
    private static SoeExamService soeExamService;

    public static void view() {

        checkContractSigned();

        User user = Auth.getCurrentUser();

        // list orders
        List<JobOrder> orders = jobOrderService.getOrdersForUser(user);

        // list courses not in orders
        List<Prestation> prestations = prestationService.getNotOrderedPrestationsByUser(Auth.getCurrentUser());
        List<Long> availableCourses = new ArrayList<Long>();

        // list SOEs not in orders
        List<YearCourse> soes = soeExamService.getNotOrderedSoesForUser(user);
        List<Long> availableSoes = new ArrayList<Long>();

        render(orders, prestations, availableCourses, soes, availableSoes);
    }

    public static void create(List<Long> availableCourses, List<Long> availableSoes) {

        checkContractSigned();

        if (null == availableCourses) {
            availableCourses = new ArrayList<Long>();
        }

        if (null == availableSoes) {
            availableSoes = new ArrayList<Long>();
        }

        if (!(!availableCourses.isEmpty() || !availableSoes.isEmpty())) {
            flashError("jobOrders.create.error.empty");
            view();
        }

        User user = Auth.getCurrentUser();

        List<Prestation> rcps = new ArrayList<Prestation>();

        for (long id : availableCourses) {

            Prestation rcp = prestationService.getNotOrderedRcpByIdAndUser(id, user);

            if (null == rcp || rcps.contains(rcp)) {
                flashError("jobOrders.create.error.invalid_ordered_course");
                view();
            }

            rcps.add(rcp);
        }

        List<SoeExam> soeExams = new ArrayList<SoeExam>();

        for (long id : availableSoes) {

            SoeExam soe = soeExamService.getNotOrderedSoeForUser(id, user);

            if (null == soe) {
                flashError("jobOrders.create.error.invalid_ordered_soe");
                view();
            }

            soeExams.add(soe);
        }

        jobOrderService.createOrder(rcps, soeExams, user);

        flashSuccess("jobOrders.create.success");
        view();
    }

    public static void show(long orderId) {

        checkContractSigned();

        JobOrder order = JobOrder.findById(orderId);
        notFoundIfNull(order);

        flashError("error.functionnality_not_implemented_yet");
        Dashboard.index();
    }

    public static void download(long orderId) {

        User user = Auth.getCurrentUser();

        JobOrder order = null;

        if (user.profile == Profile.ADMIN) {
            order = JobOrder.findById(orderId);
        } else {
            order = jobOrderService.findForUser(orderId, user);
        }

        notFoundIfNull(order);

        response.setContentTypeIfNotSet(order.pdf.type());
        renderBinary(order.pdf.get(), "Bon de commande.pdf");
    }

    private static void checkContractSigned() {

        User user = Auth.getCurrentUser();

        if (user.hasContract() && user.contract.state != ContractState.SIGNED_BY_SUPINFO) {
            flashError("jobOrders.error.contract_not_signed");
            Dashboard.index();
        }
    }
}
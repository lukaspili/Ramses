package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import models.contracts.Contract;
import models.contracts.JobOrder;
import models.contracts.JobOrderState;
import models.user.Profile;
import service.JobOrderService;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class OrdersAdmin extends AppController {

    @Inject
    private static JobOrderService jobOrderService;

    public static void signed(long orderId) {

        JobOrder order = JobOrder.findById(orderId);
        notFoundIfNull(order);

        order.state = JobOrderState.SIGNED;
        order.save();

        flashSuccess("ordersAdmin.signed.success");
        UsersAdmin.show(order.user.id);
    }
}

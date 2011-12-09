package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import models.contracts.Contract;
import models.contracts.ContractState;
import models.user.Profile;
import play.mvc.Before;
import service.ContractService;
import service.YearCourseService;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class ContractsAdmin extends AppController {

    private static PageHelper pageHelper;

    @Inject
    private static ContractService contractService;

    @Inject
    private static YearCourseService yearCourseService;

    @Before
    public static void before() {
        pageHelper = new PageHelper("contractsAdmin", renderArgs);
    }

    public static void signedBySTA(long contractId) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        if (contract.state.equals(ContractState.SIGNED_BY_SUPINFO)) {
            flashError("contractsAdmin.error.already_signed_by_supinfo");
            UsersAdmin.show(contract.user.id);
        }

        contract.state = ContractState.SIGNED_BY_STA;
        contract.save();

        flashSuccess("contractsAdmin.signedBySTA.success");
        UsersAdmin.show(contract.user.id);
    }

    public static void signedBySupinfo(long contractId) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        if (contract.state.equals(ContractState.CREATED)) {
            flashError("contractsAdmin.error.not_signed_by_sta");
            UsersAdmin.show(contract.user.id);
        }

        contract.state = ContractState.SIGNED_BY_SUPINFO;
        contract.save();

        flashSuccess("contractsAdmin.signedBySupinfo.success");
        UsersAdmin.show(contract.user.id);
    }

    public static void download(long contractId) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        response.setContentTypeIfNotSet(contract.pdf.type());
        renderBinary(contract.pdf.get(), "Contrat cadre.pdf");
    }
}

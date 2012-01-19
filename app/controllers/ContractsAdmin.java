package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.contracts.Contract;
import models.contracts.ContractState;
import models.school.YearCourse;
import models.user.Profile;
import models.user.User;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Before;
import service.ContractService;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class ContractsAdmin extends AppController {
    
    public static final String FROM_CONTRACTS_INDEX = "contractsadmin_index";

    @Inject
    private static ContractService contractService;

    @Inject
    private static YearCourseService yearCourseService;

    public static void index() {

        pageHelper().addActionTitle();

        List<Contract> contracts = contractService.getContractsWithUserByYear(YearCourseHelper.getCurrentYear());

        render(contracts);
    }

    public static void signedBySTA(long contractId, String from) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        if (contract.state.equals(ContractState.SIGNED_BY_SUPINFO)) {
            flashError("contractsAdmin.error.already_signed_by_supinfo");
            UsersAdmin.show(contract.user.id);
        }

        contract.state = ContractState.SIGNED_BY_STA;
        contract.save();

        flashSuccess("contractsadmin.signedBySTA.success");

        if(StringUtils.equals(from, FROM_CONTRACTS_INDEX)) {
            index();
        }

        UsersAdmin.show(contract.user.id);
    }

    public static void signedBySupinfo(long contractId, String from) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        if (contract.state.equals(ContractState.CREATED)) {
            flashError("contractsAdmin.error.not_signed_by_sta");
            UsersAdmin.show(contract.user.id);
        }

        contract.state = ContractState.SIGNED_BY_SUPINFO;
        contract.save();

        flashSuccess("contractsadmin.signedBySupinfo.success");

        if(StringUtils.equals(from, FROM_CONTRACTS_INDEX)) {
            index();
        }

        UsersAdmin.show(contract.user.id);
    }

    public static void download(long contractId) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        response.setContentTypeIfNotSet(contract.pdf.type());
        renderBinary(contract.pdf.get(), "Contrat cadre.pdf");
    }

    public static void regenerate(long contractId, String from) {

        Contract contract = Contract.findById(contractId);
        notFoundIfNull(contract);

        if (contract.state != ContractState.CREATED) {
            flashError("contract.regenerate.contractAlreadySigned");
            UsersAdmin.show(contract.user.id);
        }
        
        if(contract.year != YearCourseHelper.getCurrentYear()) {
            flashError("contractsadmin.regenerate.error.contract_not_for_current_year");
            UsersAdmin.show(contract.user.id);
        }

        contractService.createForUser(contract.user);

        flashSuccess("contracts.regenerate.success");
        
        if(StringUtils.equals(from, FROM_CONTRACTS_INDEX)) {
            index();
        }

        UsersAdmin.show(contract.user.id);
    }
}

package service;

import helpers.YearCourseHelper;
import models.contracts.Contract;
import models.user.User;
import pdf.ContractPdfGenerator;
import play.libs.MimeTypes;
import plugin.s3.model.impl.S3RealBlob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ContractService {

    public Contract createForUser(User user) {

        Contract contract = new Contract();
        contract.year = YearCourseHelper.getCurrentYear();

        try {
            File folder = new File("pdf/contracts");
            folder.mkdirs();

            File file = File.createTempFile("contract", ".pdf");
            new ContractPdfGenerator().generate(user, file);

            contract.pdf = new S3RealBlob();
            contract.pdf.set(new FileInputStream(file), MimeTypes.getContentType(file.getName()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (user.contract != null && user.contract.pdf.exists()) {
            user.contract.pdf.delete();
        }

        user.contract = contract;
        user.save();

        return contract;
    }


    public List<Contract> getContractsWithUserByYear(int year) {

        return Contract.em().createQuery("select c from Contract c " +
                "join c.user u " +
                "where c.year = :year " +
                "order by u.staNumber")
                .setParameter("year", year)
                .getResultList();
    }
}

package service;

import helpers.YearCourseHelper;
import models.contracts.Contract;
import models.user.User;
import pdf.ContractPdfGenerator;
import play.db.jpa.Blob;
import play.libs.MimeTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ContractService {

    public Contract createFor(User user) {

        File pdf = ContractPdfGenerator.generate(user);
        Contract contract = new Contract();
        contract.year = YearCourseHelper.getCurrentYear();
        contract.pdf = new Blob();

        try {
            contract.pdf.set(new FileInputStream(pdf), MimeTypes.getContentType(pdf.getName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (user.contract != null && user.contract.pdf != null) {
            user.contract.pdf.getFile().delete();
        }

        user.contract = contract;
        user.save();

        pdf.delete();
        return contract;
    }
}

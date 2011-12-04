package service;

import com.google.common.io.Files;
import helpers.YearCourseHelper;
import models.contracts.Contract;
import models.user.User;
import pdf.ContractPdfGenerator;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import s3.storage.S3Blob;

import java.io.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ContractService {

    public Contract createForUser(User user) {

        Contract contract = new Contract();
        contract.year = YearCourseHelper.getCurrentYear();
        contract.pdf = new S3Blob();

        try {
            File file = File.createTempFile("contract", "eunomie");
            new ContractPdfGenerator().generate(user, file);

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
}

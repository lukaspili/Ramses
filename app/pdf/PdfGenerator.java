package pdf;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.io.ByteStreams;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import org.apache.commons.io.IOUtils;
import play.Logger;
import play.Play;
import s3.storage.S3Blob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class PdfGenerator {

    private static final String GENERATED_PATH = "/generated/";

    private static final String GENERATED_EXTENSION = ".pdf";

    protected Font textBoldFont;
    protected Font textFont;
    protected Font titleFont;

    public PdfGenerator() {

        try {

            if (!FontFactory.contains("arialnarrow_normal")) {
                S3Object s3Object = S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/ARIALN.TTF");
                File arialnFile = File.createTempFile("arialn", "eunomie");
                ByteStreams.copy(s3Object.getObjectContent(), new FileOutputStream(arialnFile));

                FontFactory.register(arialnFile.getCanonicalPath(), "arialnarrow_normal");
            }

            if (!FontFactory.contains("arialnarrow_bold")) {
                S3Object s3Object = S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/ARIALNB.TTF");
                File arialnbFile = File.createTempFile("arialnb", "eunomie");
                ByteStreams.copy(s3Object.getObjectContent(), new FileOutputStream(arialnbFile));

                FontFactory.register(arialnbFile.getCanonicalPath(), "arialnarrow_bold");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        textBoldFont = FontFactory.getFont("arialnarrow_bold", 8);
        textFont = FontFactory.getFont("arialnarrow_normal", 8);
        titleFont = FontFactory.getFont("arialnarrow_bold", 14);
    }
//
//    protected File getFileForGeneration(String folder, String name) {
//
//        File folderFile = new File(GENERATED_PATH + folder);
//        if (!folderFile.exists()) {
//            folderFile.mkdirs();
//        }
//
//        return new File(folderFile, name + GENERATED_EXTENSION);
//    }

    protected File getSupinfoLogo() {

        try {
            File file = File.createTempFile("logo", "eunomie");
            ByteStreams.copy(S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/supinfo_logo.png").getObjectContent(), new FileOutputStream(file));
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

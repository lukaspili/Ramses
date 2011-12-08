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
import play.vfs.VirtualFile;
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

    private File fontFolder;
    private File imageFolder;

    public PdfGenerator() {

        try {

            fontFolder = new File("pdf/fonts");
            fontFolder.mkdirs();

            imageFolder = new File("pdf/images");
            imageFolder.mkdirs();

            if (!FontFactory.contains("arialnarrow_normal")) {

                File arialnFile = Play.getFile("/app/pdf/resources/ARIALN.TTF");
                Logger.info("PDF GENERATION : Getting file from " + arialnFile.getAbsolutePath());

                if (arialnFile.exists()) {
                    Logger.info("PDF GENERATION : Local ARIALN.TTF exists");

                } else {

                    Logger.info("PDF GENERATION : Local ARIALN.TTF dosen't exist, getting from amazon s3");

                    arialnFile = new File(fontFolder, "ARIALN.TTF");

                    if (!arialnFile.exists()) {
                        S3Object s3Object = S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/ARIALN.TTF");
                        ByteStreams.copy(s3Object.getObjectContent(), new FileOutputStream(arialnFile));
                    }
                }

                FontFactory.register(arialnFile.getCanonicalPath(), "arialnarrow_normal");
            }

            if (!FontFactory.contains("arialnarrow_bold")) {

                File arialnbFile = Play.getFile("/app/pdf/resources/ARIALNB.TTF");
                Logger.info("PDF GENERATION : Getting file from " + arialnbFile.getAbsolutePath());

                if (arialnbFile.exists()) {
                    Logger.info("PDF GENERATION : Local ARIALNB.TTF exists");

                } else {

                    Logger.info("PDF GENERATION : Local ARIALNB.TTF dosen't exist, getting from amazon s3");

                    arialnbFile = new File(fontFolder, "ARIALNB.TTF");

                    if (!arialnbFile.exists()) {
                        S3Object s3Object = S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/ARIALNB.TTF");
                        ByteStreams.copy(s3Object.getObjectContent(), new FileOutputStream(arialnbFile));
                    }
                }

                FontFactory.register(arialnbFile.getCanonicalPath(), "arialnarrow_bold");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        textBoldFont = FontFactory.getFont("arialnarrow_bold", 8);
        textFont = FontFactory.getFont("arialnarrow_normal", 8);
        titleFont = FontFactory.getFont("arialnarrow_bold", 14);
    }

    protected File getSupinfoLogo() {

        File file = Play.getFile("/app/pdf/resources/supinfo_logo.png");
        Logger.info("PDF GENERATION : Getting file from " + file.getAbsolutePath());

        if (file.exists()) {
            Logger.info("PDF GENERATION : Local supinfo_logo.png exists");
            return file;
        }

        try {
            Logger.info("PDF GENERATION : Local supinfo_logo.png dosen't exist, getting from amazon s3");

            file = new File(imageFolder, "supinfo_logo.png");

            if (!file.exists()) {
                S3Object object = S3Blob.s3Client.getObject(S3Blob.s3Bucket, "resources/supinfo_logo.png");
                ByteStreams.copy(object.getObjectContent(), new FileOutputStream(file));
            }

            return file;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.contracts.JobOrder;
import models.school.SoeExam;
import models.user.User;
import org.joda.time.LocalDate;
import play.Logger;
import play.Play;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JobOrderPdfGenerator {

    private static final String PATH = "generated/orders";
    private static final String EXTENSION = ".pdf";

    public static File generate(JobOrder order) {

        Logger.debug("Generate order : " + order);

        File folder = new File(PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "order-" + new Date().getTime() + "-" + order.user.id + EXTENSION);

        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            FontFactory.register(Play.getFile("/public/pdf/ARIALN.ttf").getPath(), "arialnarrow_normal");
            FontFactory.register(Play.getFile("/public/pdf/ARIALNB.ttf").getPath(), "arialnarrow_bold");

            Font textBoldFont = FontFactory.getFont("arialnarrow_bold", 8);
            Font textFont = FontFactory.getFont("arialnarrow_normal", 8);
            Font titleFont = FontFactory.getFont("arialnarrow_bold", 14);

            int textLeading = 9;
            int clauseSpacing = 7;

            PdfPTable table;
            PdfPCell cell;

            List list;
            ListItem item;

            Paragraph paragraph;
            Phrase phrase;
            Image image;

            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            image = Image.getInstance(Play.getFile("/public/pdf/supinfo_logo.png").getPath());
            image.scaleAbsolute(170, 56);

            cell = new PdfPCell(image, false);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();


            phrase = new Phrase("BON DE COMMANDE\n" +
                    "N°" + PdfGeneratorFormatter.getFormattedOrderId(order.id) + "\n" +
                    "Contrat N°2012-PAR-" + PdfGeneratorFormatter.getFormattedUserId(order.user.id), titleFont);
            phrase.setLeading(25);
            cell.setPhrase(phrase);
            cell.setLeading(3, 1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            document.add(table);

            int leftIndent = 360;

            paragraph = new Paragraph("Monsieur " + order.user.firstName + " " + order.user.lastName.toUpperCase() + "\n", textBoldFont);
            paragraph.setIndentationLeft(leftIndent);
            paragraph.setLeading(textLeading);
            paragraph.setSpacingBefore(30);
            document.add(paragraph);

            paragraph = new Paragraph(order.user.street + "\n" + order.user.postalCode + " - " + order.user.city + ", FRANCE", textFont);
            paragraph.setIndentationLeft(leftIndent);
            paragraph.setLeading(textLeading);
            document.add(paragraph);

            LocalDate today = new LocalDate();
            paragraph = new Paragraph("Paris, le " + today.getDayOfMonth() + " / " + today.getDayOfMonth() + " / " + today.getYear(), textFont);
            paragraph.setIndentationLeft(leftIndent);
            paragraph.setSpacingBefore(2);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);


            paragraph = new Paragraph("Bon pour commande,\nLa Directrice du Campus,", textBoldFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);

            paragraph = new Paragraph("Marie-Christine FRENDO.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }
}

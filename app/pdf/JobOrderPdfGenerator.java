package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.contracts.JobOrder;
import models.school.SoeExam;
import models.school.YearCourse;
import org.joda.time.LocalDate;
import play.Logger;
import play.db.jpa.Blob;
import play.libs.I18N;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JobOrderPdfGenerator extends PdfGenerator {

    private static final String FOLDER = "orders";

    public void generate(JobOrder order, File file) {

        Logger.debug("Generate order : " + order);

        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            int textLeading = 9;

            PdfPTable table;
            PdfPCell cell;

            Paragraph paragraph;
            Phrase phrase;
            Image image;

            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            image = Image.getInstance(getSupinfoLogo().getPath());
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
            paragraph = new Paragraph("Paris, le " + today.getDayOfMonth() + " / " + today.getMonthOfYear() + " / " + today.getYear(), textFont);
            paragraph.setIndentationLeft(leftIndent);
            paragraph.setSpacingBefore(2);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);


            table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 1, 1, 1, 1, 1, 1});

            cell = new PdfPCell();
            phrase = new Phrase("Intitulé du cours", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Code du cours", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Classe", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Début du cours", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Fin du cours", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Durée", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Tarif horaire", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Total", textBoldFont);
            phrase.setLeading(textLeading);
            cell.setPhrase(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            for (YearCourse course : order.courses) {

                phrase = new Phrase(course.course.name, textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                table.addCell(cell);

                phrase = new Phrase(course.course.code, textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(course.course.promotion.getLabel(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(course.startDate.toString(I18N.getDateFormat()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(course.endDate.toString(I18N.getDateFormat()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(course.duration), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(course.course.type.price), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(course.getTotal()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (SoeExam soeExam : order.soeExams) {

                phrase = new Phrase("SOE " + soeExam.course.course.name, textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                table.addCell(cell);

                phrase = new Phrase(soeExam.course.course.code, textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(soeExam.course.course.promotion.getLabel(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(soeExam.date.toString(I18N.getDateFormat()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(soeExam.date.toString(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(soeExam.plannifiedDuration), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(SoeExam.PRICE), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(soeExam.getTotal()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            phrase = new Phrase("Total Commande, EUR TTC", textBoldFont);
            phrase.setLeading(textLeading);
            cell = new PdfPCell();
            cell.setPhrase(phrase);
            cell.setColspan(7);
            table.addCell(cell);

            phrase = new Phrase(String.valueOf(order.total), textBoldFont);
            phrase.setLeading(textLeading);
            cell = new PdfPCell();
            cell.setPhrase(phrase);
            cell.setColspan(7);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);


            paragraph = new Paragraph("Bon pour commande,\nLa Directrice du Campus,", textBoldFont);
            paragraph.setLeading(textLeading);
            paragraph.setSpacingBefore(30);
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
    }
}

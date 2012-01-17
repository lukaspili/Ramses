package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.contracts.JobOrder;
import models.school.Prestation;
import models.school.SoeExam;
import org.joda.time.LocalDate;
import play.Logger;
import play.libs.I18N;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
                    "N°" + order.getFormattedId() + "\n" +
                    "Contrat N°2012-PAR-" + order.user.getFormattedStaNumber(), titleFont);
            phrase.setLeading(25);
            cell.setPhrase(phrase);
            cell.setLeading(3, 1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Campus de Paris");
            cell.setPhrase(phrase);
            cell.setIndent(35);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();
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
            for (Prestation prestation : order.realCoursesProfessors) {
                if (today.isAfter(prestation.realCourse.startDate)) {
                    today = prestation.realCourse.startDate;
                }
            }

            for(SoeExam soeExam : order.soeExams) {
                if (today.isAfter(soeExam.date)) {
                    today = soeExam.date;
                }
            }
            
            String month = String.valueOf(today.getMonthOfYear());
            if(month.length() == 1) {
                month = "0" + month;
            }

            String day = String.valueOf(today.getDayOfMonth());
            if(day.length() == 1) {
                day = "0" + day;
            }

            paragraph = new Paragraph("Paris, le " + day + " / " + month + " / " + today.getYear(), textFont);
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


            for (Prestation rcp : order.realCoursesProfessors) {

                Logger.debug("Add prestation to joborder");

                phrase = new Phrase(rcp.realCourse.yearCourse.getFullName(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                table.addCell(cell);

                phrase = new Phrase(rcp.realCourse.yearCourse.course.getFullCode(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(rcp.realCourse.yearPromotion.getFullName(), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(rcp.realCourse.startDate.toString(I18N.getDateFormat()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(rcp.realCourse.endDate.toString(I18N.getDateFormat()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(rcp.hours), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(rcp.realCourse.yearCourse.type.getPriceForYearPromotion(rcp.realCourse.yearPromotion)), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                phrase = new Phrase(String.valueOf(rcp.getTotal()), textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (SoeExam soeExam : order.soeExams) {

                Logger.debug("Add soe exam to joborder");

                phrase = new Phrase("SOE " + soeExam.course.course.name, textFont);
                phrase.setLeading(textLeading);
                cell = new PdfPCell();
                cell.setPhrase(phrase);
                table.addCell(cell);

                phrase = new Phrase(soeExam.course.course.getFullCode(), textFont);
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

                phrase = new Phrase(soeExam.date.toString(I18N.getDateFormat()), textFont);
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

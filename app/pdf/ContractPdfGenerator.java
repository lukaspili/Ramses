package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.contracts.Contract;
import models.user.User;
import play.Logger;
import play.Play;
import play.db.jpa.Blob;
import play.libs.MimeTypes;

import javax.activation.MimeType;
import java.io.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ContractPdfGenerator {

    private static final String PATH = "generated/contracts";
    private static final String EXTENSION = ".pdf";

    public static File generate(User user) {

        Logger.debug("Generate contract for user : " + user);

        File folder = new File(PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, user.id + EXTENSION);

        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            Font textBoldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 8);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

            PdfPTable table;
            PdfPCell cell;
            Paragraph paragraph;
            Image image;

            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            image = Image.getInstance(Play.getFile("/public/pdf/supinfo_logo.png").getPath());
            image.scaleAbsolute(170, 56);
            
            cell = new PdfPCell(image, false);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("CONTRAT DE PRESTATION", titleFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            paragraph = new Paragraph("PEDAGOGIQUE SANS EXCLUSIVITE", titleFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            paragraph = new Paragraph("n°2012-PAR-01", titleFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            document.add(table);

            paragraph = new Paragraph("Entre", textFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            cell = new PdfPCell();

            paragraph = new Paragraph("PARIS INTERNATIONAL CAMPUS SA (SUPINFO),", textBoldFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("immatriculée au RCS de Paris sous le numéro 511 930 083,", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("et ayant son siège au 52 rue de Bassano, 75008 PARIS, France,", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("représentée aux présentes par Monsieur Olivier COMES, Président,", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("ci-après dénommée \"SUPINFO\", d'une part,", textFont);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("et M. Lukasz PILISZCZUK", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("Immatriculé à Evry sous le numéro 515 054 849 00010,", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("et domicilié 7 T route de Paris - 91530 Saint-Chéron,", textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("ci-après dénommé « Le Prestataire », d'autre part,", textFont);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(20);
            table.addCell(cell);

            document.add(table);

            paragraph = new Paragraph("il a été convenu ce qui suit.", textFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            document.add(new Paragraph("PREAMBULE", textBoldFont));
            document.add(new Paragraph("On entend par SUPINFO toutes les structures juridiques expressément autorisées à représenter la marque SUPINFO dans le monde entier, " +
                    "soit dans le cadre d’une gestion directe par l’Association Ecole Supérieure d’Informatique – SUPINFO, soit dans le cadre d’accord commerciaux, de partenariats, " +
                    "de représentation ou de franchise existants au moment de la signature des présentes ou à venir.", textFont));

            document.add(new Paragraph("ARTICLE 1 : MISSION, sans exclusivite", textBoldFont));
            document.add(new Paragraph("Dans le cadre de l’activité pédagogique de SUPINFO, cette dernière confie au Prestataire la mission de réaliser des activités pédagogiques sur un de ses campus. Le prestataire exerce son activité en toute " +
                    "indépendance, sans aucun lien de subordination avec SUPINFO. Il s’engage à l’exécution d’une tâche nettement définie. Dans ce cadre il assurera la responsabilité de l’exécution de la prestation. Pour ce faire il recevra une " +
                    "rémunération définie à l’article 4. Aucune exclusivité n’est attachée à ladite mission. Le Prestataire sera libre de prendre d’autres missions auprès d’autres établissements d’enseignements publics ou privés, " +
                    "à charge pour lui d’en informer SUPINFO.", textFont));

            document.add(new Paragraph("ARTICLE 2 : DUREE"));
            document.add(new Paragraph("Le Contrat prend effet à la date de sa signature par les deux Parties et prendra fin le 30 juin 2012"));

            document.add(new Paragraph("ARTICLE 3 : LES ACTIVITES PEDAGOGIQUES"));
            document.add(new Paragraph("Les activités pédagogiques pourront être de plusieurs types : cours sans TP noté ni mini projet, cours avec TP ou mini projet, khôlles seules, accompagnement en formation à distance ou tutorat. Les marchés conclus sur la base de cet accord cadre pourront être fractionnés. Ils feront l’objet de bons de commande, mentionnant la matière, la durée, le type d’activités, le planning d’intervention, le tarif d’intervention. Ces bons de commande seront signés par le prestataire et le Directeur du Campus de SUPINFO. Il pourra y avoir plusieurs marchés liés à plusieurs bons de commande pendant la durée du contrat."));

            document.add(new Paragraph("ARTICLE 4 : TARIF ET HONORAIRES"));
            document.add(new Paragraph("Les Parties conviennent que le tarif des honoraires versés au Prestataire par SUPINFO est le suivant :"));

            document.add(new Paragraph("ARTICLE 5: FACTURATION"));
            document.add(new Paragraph("Une avance de 50% du premier marché faisant l’objet d’une facture du Prestataire, plafonnée à 500 EUR (cinq cent euros) TTC, sera versée à la commande. La facturation du solde des prestations pédagogiques dispensées interviendra en fin de contrat, sur la base de l’ensemble des heures de cours effectivement dispensées en référence aux marchés commandés et après que les notes et les corrections des copies des élèves aient délivrées à SUPINFO déduction faite des éventuelles pénalités de retard de remise des notes. Les factures seront payées dans un délai de 15 jours à compter de leur date de réception à l’adresse suivante exclusivement : Paris International Campus - Comptabilité Fournisseurs - 52 rue de Bassano - 75008 Paris."));

            document.add(new Paragraph("ARTICLE 6 : EXECUTION DE LA MISSION"));
            document.add(new Paragraph("Pour chaque marché, le Prestataire dispensera les heures de cours mentionnées sur le bon de commande en respectant le planning qui y est mentionné. Des modifications de planning peuvent intervenir pour " +
                    "chaque marché durant la période d’exécution du présent contrat. Elles ne peuvent avoir lieu qu’à l’initiative de SUPINFO. Le Prestataire en sera informé et un compromis sera recherché. En cas de désaccord, SUPINFO reste décisionnaire de leur application ou non. Toute annulation d’heures de cours prononcée par SUPINFO, et pour laquelle le Prestataire sera tenu informé formellement par email au moins 10 jours calendaires avant le début de la prestation ne donnera lieu à aucun dédommagement. En deçà d’une semaine de préavis, toute annulation d’heures de cours donnera lieu à un dédommagement pour le Prestataire correspondant, au nombre d’heures de cours annulées hors du délai de préavis, multiplié par la moitié du prix de l’heure fixée dans le présent contrat. Il est convenu entre les parties qu’une session de cours planifiée ne peut en aucun cas être décalée de plus de 2 semaines par le prestataire. En cas d’annulation de la session de formation par le Prestataire dans les dix jours ouvrés avant le début de la formation, le Formateur devra proposer à SUPINFO :"));

            document.add(new Paragraph("ARTICLE 7 : LIEUX / PERSONNELS DES PARTIES"));
            document.add(new Paragraph("Quel que soit le lieu où il accomplit sa mission, le personnel de chaque partie reste toujours sous l'entière responsabilité de son employeur et n'est jamais placé sous la responsabilité civile de l'autre partie. Lorsque des travaux sont exécutés dans les locaux de l'autre partie, le personnel de chacune des parties se conformera aux prescriptions en vigueur dans l'établissement où il effectue sa mission, notamment le règlement intérieur, les consignes de sécurité et les horaires habituels de celui-ci."));

            document.add(new Paragraph("ARTICLE 8 : PUBLICITE"));
            document.add(new Paragraph("SUPINFO peut se prévaloir du présent partenariat avec le Prestataire auprès des étudiants ou d'autres institutions. Chaque partie se réserve le droit de modifier ses marques unilatéralement. Chaque partie s'engage à utiliser les logotypes et marques de l'autre partie dans le strict respect des prescriptions de l'article 6, et notamment en respectant la charte graphique de l'autre partie."));

            document.add(new Paragraph("ARTICLE 9 : PROPRIÉTÉ INTELLECTUELLE"));
            document.add(new Paragraph("Le Prestataire s'engage à céder au profit de SUPINFO, au fur et à mesure de leurs créations, de l’ensemble des droits d’exploitation des contributions que le Prestataire fournira dans le cadre du présent contrat (notamment contributions à la création de supports de cours, tutoriels, vidéos de présentations pédagogiques), à l'exception des produits, logiciels, signes distinctifs ou documentations de le Prestataire. En conséquence, SUPINFO acquiert, de façon définitive, la titularité de l’ensemble des droits de représentation et de reproduction sur ces contributions réalisées par le Prestataire, à l'exception des produits, logiciels, signes distinctifs ou documentation du Prestataire, dans le cadre du présent contrat :"));

            document.add(new Paragraph("ARTICLE 10 : CONFIDENTIALITE"));
            document.add(new Paragraph("Chacune des parties convient de garder secrètes et confidentielles par tous les moyens raisonnables et à faire garder par ses personnels ou les personnels qu'elle ferait travailler directement ou par personne interposée les informations secrètes et confidentielles de l'autre partie auxquelles elle aurait pu avoir accès pour l'exécution du Contrat. Les parties ne seront tenues de protéger que les seules Informations qui sont divulguées sous une forme tangible et clairement identifiées comme confidentielles ou protégées, lors de leur divulgation ou initialement divulguées sous une forme non-tangible et considérées comme confidentielles ou protégées lors de leur divulgation, et qui seront ensuite résumées par écrit et désignées comme confidentielles ou protégées, et communiquées dans les trente jours suivant leur date de divulgation initiale (ci-après les \"Informations Confidentielles\")."));

            document.add(new Paragraph("ARTICLE 11 : FORCE MAJEURE"));
            document.add(new Paragraph("Aucune des parties ne pourra être tenue pour responsable d'un retard d'exécution, d'une exécution partielle ou d'un défaut d'exécution d'une obligation mise à sa charge par le Contrat qui serait du à un événement présentant les caractéristiques de la force majeure au regard de la jurisprudence des juridictions française (la \"Force Majeure\"), à condition toutefois que la partie concernée notifie rapidement par écrit à l'autre partie l'existence de cette Force Majeure et reprenne ses obligations dès que possible."));

            document.add(new Paragraph("ARTICLE 12 : NULLITE PARTIELLE ET TOLERANCE"));
            document.add(new Paragraph("La déclaration de nullité ou d'inefficacité d'une quelconque stipulation du Contrat n'entrainera pas de plein droit la nullité ou l'inefficacité des autres stipulations. La tolérance d'une partie aux inexécutions de l'autre partie ne fait pas présumer une future tolérance de la même ou d'autres inexécutions pas plus qu'elle ne caractérise l'intention de l'une ou l'autre des parties de renoncer à faire respecter les droits dont elle dispose au titre du Contrat."));

            document.add(new Paragraph("ARTICLE 13 : DROIT APPLICABLE / ATTRIBUTION DE COMPETENCE"));
            document.add(new Paragraph("Le Contrat est soumis au droit français. Pour le cas où les parties ne pourraient pas régler leurs différends éventuels à l'amiable, elles conviennent expressément de les soumettre, où que soient les lieux d'exécution des prestations ou les sièges sociaux des parties, à la compétence exclusive du Tribunal de Commerce de Par"));

            document.add(new Paragraph("Fait à Paris, le 14 octobre 2011, en deux exemplaires originaux."));

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }
}

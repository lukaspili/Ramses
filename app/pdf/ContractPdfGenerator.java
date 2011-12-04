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
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ContractPdfGenerator extends PdfGenerator {

    private static final String FOLDER = "contracts";

    public File generate(User user) {

        Logger.debug("Generate contract for user : " + user);

        File file = getFileForGeneration(FOLDER, "contract-" + new Date().getTime() + "-" + user.id);

        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

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

            image = Image.getInstance(getSupinfoLogo().getPath());
            image.scaleAbsolute(170, 56);

            cell = new PdfPCell(image, false);
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();

            phrase = new Phrase("CONTRAT DE PRESTATION\nPEDAGOGIQUE SANS EXCLUSIVITE\n" +
                    "n°2012-PAR-" + PdfGeneratorFormatter.getFormattedUserId(user.id), titleFont);
            phrase.setLeading(25);
            cell.setPhrase(phrase);
            cell.setLeading(3, 1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
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
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);
            paragraph = new Paragraph("et ayant son siège au 52 rue de Bassano, 75008 PARIS, France,", textFont);
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);
            paragraph = new Paragraph("représentée aux présentes par Monsieur Olivier COMES, Président,", textFont);
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);
            paragraph = new Paragraph("ci-après dénommée \"SUPINFO\", d'une part,", textFont);
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("et M. " + user.firstName + " " + user.lastName.toUpperCase(), textFont);
            cell.addElement(paragraph);
            paragraph = new Paragraph("Immatriculé à " + user.rcs + " sous le numéro " + user.siret + ",", textFont);
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);
            paragraph = new Paragraph("et domicilié " + user.street + " - " + user.postalCode + " " + user.city + ",", textFont);
            paragraph.setLeading(textLeading);
            cell.addElement(paragraph);
            paragraph = new Paragraph("ci-après dénommé « Le Prestataire », d'autre part,", textFont);
            paragraph.setLeading(textLeading);
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
            paragraph = new Paragraph("On entend par SUPINFO toutes les structures juridiques expressément autorisées à représenter la marque SUPINFO dans le monde entier, " +
                    "soit dans le cadre d’une gestion directe par l’Association Ecole Supérieure d’Informatique – SUPINFO, soit dans le cadre d’accord commerciaux, de partenariats, " +
                    "de représentation ou de franchise existants au moment de la signature des présentes ou à venir.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 1 : MISSION, sans exclusivite", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Dans le cadre de l’activité pédagogique de SUPINFO, cette dernière confie au Prestataire la mission de réaliser des activités pédagogiques sur un de ses campus. Le prestataire exerce son activité en toute " +
                    "indépendance, sans aucun lien de subordination avec SUPINFO. Il s’engage à l’exécution d’une tâche nettement définie. Dans ce cadre il assurera la responsabilité de l’exécution de la prestation. Pour ce faire il recevra une " +
                    "rémunération définie à l’article 4. Aucune exclusivité n’est attachée à ladite mission. Le Prestataire sera libre de prendre d’autres missions auprès d’autres établissements d’enseignements publics ou privés, " +
                    "à charge pour lui d’en informer SUPINFO.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 2 : DUREE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Le Contrat prend effet à la date de sa signature par les deux Parties et prendra fin le 30 juin 2012", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 3 : LES ACTIVITES PEDAGOGIQUES", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Les activités pédagogiques pourront être de plusieurs types : cours sans TP noté ni mini projet, cours avec TP ou mini projet, khôlles seules, " +
                    "accompagnement en formation à distance ou tutorat. Les marchés conclus sur la base de cet accord cadre pourront être fractionnés. Ils feront l’objet de bons de commande, mentionnant la matière, la durée, le type d’activités, le planning d’intervention, le tarif d’intervention. Ces bons de commande seront signés par le prestataire et le Directeur du Campus de SUPINFO. Il pourra y avoir plusieurs marchés liés à plusieurs bons de commande pendant la durée du contrat.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 4 : TARIF ET HONORAIRES", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Les Parties conviennent que le tarif des honoraires versés au Prestataire par SUPINFO est le suivant :", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{5, 1, 1});

            cell = new PdfPCell();

            paragraph = new Paragraph("Types d'interventions pédagogiques", textFont);
            paragraph.setLeading(textLeading);
            cell.setPaddingTop(10);
            cell.setPaddingLeft(3);
            cell.addElement(paragraph);

            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("Effectif de la classe\n supérieur\n à 75 élèves", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(3);

            cell.addElement(paragraph);

            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("Effectif de la classe\n inférieur ou égal\n à 75 élèves", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(3);

            cell.addElement(paragraph);

            table.addCell(cell);


            cell = new PdfPCell();
            phrase = new Phrase("Cours sans TP noté ni mini projet en EUR TTC par heure", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("30,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("20,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Cours avec TP ou mini projet en EUR TTC par heure", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("37,50", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("25,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Khôlles seules en EUR TTC par heure", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("20,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("20,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Accompagnement en formation à distance ou en tutorat sans TP noté ni mini projet en EUR TTC par heure", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("20,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("20,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("Accompagnement en formation à distance ou en tutorat avec TP noté ou mini projet en EUR TTC par heure", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("25,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("25,00", textFont);
            cell.setPhrase(phrase);
            cell.setPaddingLeft(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            paragraph = new Paragraph("Il s’agit d’honoraires fixes et forfaitaires incluant :", textFont);
            paragraph.setSpacingAfter(2);
            document.add(paragraph);

            list = new List(List.UNORDERED);
            list.setIndentationLeft(18);

            item = new ListItem(textLeading, "La préparation personnelle du cours ou des évaluations (cours, accompagnement à distance, tutorat, colles,) à partir des supports établis par le professeur référent de la matière enseignée ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "L’acte pédagogique interactif (cours, accompagnement à distance - tutorat - khôlles) en respectant les dates et horaires de cours inscrits au planning des étudiants ainsi que le scénario du cours établi et communiqué par le professeur référent (full professor) de la matière enseignée ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le cas échéant, la surveillance et la correction des Travaux Pratiques notés (TP notés), examens, exercices, projets ou dossiers réalisés par les étudiants, évaluations continues, cours avec TP ou mini projet, accompagnement à distance- tutorat avec TP noté ou projet ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "La saisie dans le système d’information de SUPINFO (Open Campus) des notes de TP notés, examens, exercices, projets ou dossiers selon les indications techniques données par SUPINFO. La correction, la remise des corrigés ainsi que la saisie des notes de TP notés, examens, exercices, projets ou dossiers, devront intervenir dans un délai maximum de 15 jours après chaque session d’évaluation continue. En cas de dépassement de ce délai, une pénalité de retard de 50,00 EUR (cinquante euros) toutes taxes comprises par jour ouvré sera déduite des montants dus au Prestataire par SUPINFO.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le suivi des présences des étudiants relevant de la formation professionnelle continue (notamment en contrat de professionnalisation) : le Prestataire s’engage à faire signer à chacun des étudiants pour chacun des cours qu’il dispense, pour chaque demi-journée, en double exemplaire, une feuille de présence. Il vérifiera que chaque feuille de présence comporte la date, les heures de début et fin de chaque demi-journée de cours, le nom et la signature du Prestataire, le nom de la matière ou du cours enseigné, le nom et la signature de chaque étudiant par demi-journée. Il en remettra un exemplaire à l’accueil de son campus de formation à la fin de chaque journée. En cas de dépassement de ce délai de plus de 3 jours, une pénalité de retard de 50,00 EUR (cinquante euros) toutes taxes comprises par jour ouvré sera déduite des montants dus au Prestataire par SUPINFO.", textFont);
            list.add(item);

            document.add(list);


            paragraph = new Paragraph("ARTICLE 5: FACTURATION", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Une avance de 50% du premier marché faisant l’objet d’une facture du Prestataire, plafonnée à 500 EUR (cinq cent euros) TTC, sera versée à la commande. La facturation du solde des prestations pédagogiques " +
                    "dispensées interviendra en fin de contrat, sur la base de l’ensemble des heures de cours effectivement dispensées en référence aux marchés commandés et après que les notes et les corrections des copies des élèves aient délivrées à SUPINFO déduction faite des éventuelles pénalités de retard de remise des notes. Les factures seront payées dans un délai de 15 jours à compter de leur date de réception à l’adresse suivante exclusivement : Paris International Campus - Comptabilité Fournisseurs - 52 rue de Bassano - 75008 Paris.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 6 : EXECUTION DE LA MISSION", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Pour chaque marché, le Prestataire dispensera les heures de cours mentionnées sur le bon de commande en respectant le planning qui y est mentionné. Des modifications de planning peuvent intervenir pour " +
                    "chaque marché durant la période d’exécution du présent contrat. Elles ne peuvent avoir lieu qu’à l’initiative de SUPINFO. Le Prestataire en sera informé et un compromis sera recherché. En cas de désaccord, " +
                    "SUPINFO reste décisionnaire de leur application ou non. Toute annulation d’heures de cours prononcée par SUPINFO, et pour laquelle le Prestataire sera tenu informé formellement par email au moins 10 jours calendaires avant le début de la prestation ne donnera lieu à aucun dédommagement. En deçà d’une semaine de préavis, toute annulation d’heures de cours donnera lieu à un dédommagement pour le Prestataire correspondant, au nombre d’heures de cours annulées hors du délai de préavis, multiplié par la moitié du prix de l’heure fixée dans le présent contrat. Il est convenu entre les parties qu’une session de cours planifiée ne peut en aucun cas être décalée de plus de 2 semaines par le prestataire. En cas d’annulation de la session de formation par le Prestataire dans les dix jours ouvrés avant le début de la formation, le Formateur devra proposer à SUPINFO :", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);

            list = new List(List.UNORDERED);
            list.setIndentationLeft(18);

            item = new ListItem(textLeading, "soit un report de session permettant de dispenser la session de cours dans un délai maximum de 2 semaines", textFont);
            list.add(item);

            item = new ListItem(textLeading, "soit un prestataire pouvant le remplacer, après accord de SUPINFO, et pouvant dispenser la session de cours dans un délai maximum de 2 semaines.", textFont);
            list.add(item);

            document.add(list);

            paragraph = new Paragraph("Dans le cas où le prestataire ne serait pas en mesure de répondre à ces deux exigences, SUPINFO serait en droit de demander au prestataire réparation du préjudice subit égal au montant TTC de la prestation qui n’aurait pas été intégralement délivrée.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 7 : LIEUX / PERSONNELS DES PARTIES", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Quel que soit le lieu où il accomplit sa mission, le personnel de chaque partie reste toujours sous l'entière responsabilité de son employeur et n'est jamais placé sous la responsabilité civile de l'autre " +
                    "partie. Lorsque des travaux sont exécutés dans les locaux de l'autre partie, le personnel de chacune des parties se conformera aux prescriptions en vigueur dans l'établissement où il effectue sa mission, notamment le règlement intérieur, les consignes de sécurité et les horaires habituels de celui-ci.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 8 : PUBLICITE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("SUPINFO peut se prévaloir du présent partenariat avec le Prestataire auprès des étudiants ou d'autres institutions. Chaque partie se réserve le droit de modifier ses marques unilatéralement. Chaque partie " +
                    "s'engage à utiliser les logotypes et marques de l'autre partie dans le strict respect des prescriptions de l'article 6, et notamment en respectant la charte graphique de l'autre partie.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 9 : PROPRIÉTÉ INTELLECTUELLE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Le Prestataire s'engage à céder au profit de SUPINFO, au fur et à mesure de leurs créations, de l’ensemble des droits d’exploitation des contributions que le Prestataire fournira dans le cadre du présent " +
                    "contrat (notamment contributions à la création de supports de cours, tutoriels, vidéos de présentations pédagogiques), à l'exception des produits, logiciels, signes distinctifs ou documentations de le Prestataire. En conséquence, SUPINFO acquiert, de façon définitive, la titularité de l’ensemble des droits de représentation et de reproduction sur ces contributions réalisées par le Prestataire, à l'exception des produits, logiciels, signes distinctifs ou documentation du Prestataire, dans le cadre du présent contrat :", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);

            list = new List(List.UNORDERED);
            list.setIndentationLeft(18);

            item = new ListItem(textLeading, "Le droit de numériser ou faire numériser, par compression, compactage et autres procédés, reproduire ou faire reproduire, les contributions en tout ou partie, sous tout format et quelque support que ce soit connu ou inconnu à ce jour, et notamment à titre d’exemple, sans que cette liste ne soit limitative, sur toute édition papier/graphique, numérique, opto-numérique, pellicule, film, affiche, catalogue, phonogramme et/ou vidéogramme et/ou tous supports multimédias ainsi que leur conditionnement et/ou service en ligne, sous quelque marques, étiquettes et/ou label au choix de SUPINFO et/ou ses contractants.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit de numériser ou faire numériser, par compression, compactage et autres procédés, reproduire ou faire reproduire, les contributions en tout ou partie, sous tout format et quelque support que ce soit connu ou inconnu à ce jour, et notamment à titre d’exemple, sans que cette liste ne soit limitative, sur toute édition papier/graphique, numérique, opto-numérique, pellicule, film, affiche, catalogue, phonogramme et/ou vidéogramme et/ou tous supports multimédias ainsi que leur conditionnement et/ou service en ligne, sous quelque marques, étiquettes et/ou label au choix de SUPINFO et/ou ses contractants.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit d’établir ou de faire établir, en nombre qu’il plaira à SUPINFO ou à ses ayant-droits, tous originaux, doubles ou copies de la version définitive des contributions, sur tous supports analogiques ou numériques.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit de reproduire tout extrait, partie ou totalité des contributions, et tout extrait, pour l’intégration dans tous supports tels que notamment visés ci-avant.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "le droit de reproduction, de représentation et de communication publique des contributions, en tout ou partie, sur tous supports, par tous modes (et notamment par télédiffusion, et par tous réseaux et système de télécommunication et télétransmission) pour les exploitations promotionnelles, publicitaires et/ou toutes actions de communication ; et au sein d’œuvres audiovisuelles, graphiques, électroniques, numériques ou autres, ou sur leur matériel de présentation.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit d’exploiter les contributions par tous procédés audiovisuels non encore connu à ce jour.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit d’exploiter les contributions sous forme d’extraits, par intégration et sans modification, dans un programme multimédia interactif pouvant être exploité sur tous supports destinés à la vente, à la location ou au prêt pour l’usage privé du public ou par télédiffusion par voie hertzienne terrestre, par satellite ou en réseau. Par programme multimédia interactif, on entend un ensemble homogène, lors de la consultation, composé d’éléments de genres différents et notamment de textes, de sons, d’images fixes ou animées, dont la structure et l’accès sont régis par un logiciel permettant l’interactivité.", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit d’exploiter les contributions sous forme d’extraits, « en ligne » et/ou sur support électronique et/ou numérique, notamment par l’intermédiaire de réseaux de télécommunication, et/ou d’un système télématique interactif, tels que par exemple sur les réseaux Internet, Intranet, réseaux de téléphonie mobile, service dits « online ».", textFont);
            list.add(item);

            item = new ListItem(textLeading, "Le droit d’adapter les contributions, y compris par extraits, dans les conditions visées aux points ci-avant, sur tout matériel se rapportant à l’activité de SUPINFO, et à tous les produits édités par SUPINFO, tels que notamment, les affiches, catalogues, phonogrammes, vidéogrammes, sous toute taille et quelque forme que soit leur résolution.", textFont);
            list.add(item);

            document.add(list);

            paragraph = new Paragraph("Cette cession est consentie pour le monde entier pour la durée légale de protection des droits d’auteur, telle que définie par la législation française. Cette cession telle que définie ci-dessus est définitive et la rupture du présent contrat et ce, quelle que soit sa cause, n’affectera en aucune façon l’étendue et la durée de la présente cession. Il est de convention expresse entre les parties qu’aucun autre écrit ne sera nécessaire pour constater la présente cession du Prestataire au profit de SUPINFO.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 10 : CONFIDENTIALITE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Chacune des parties convient de garder secrètes et confidentielles par tous les moyens raisonnables et à faire garder par ses personnels ou les personnels qu'elle ferait travailler directement ou par personne" +
                    " interposée les informations secrètes et confidentielles de l'autre partie auxquelles elle aurait pu avoir accès pour l'exécution du Contrat. Les parties ne seront tenues de protéger que les seules Informations qui sont divulguées sous une forme tangible et clairement identifiées comme confidentielles ou protégées, lors de leur divulgation ou initialement divulguées sous une forme non-tangible et considérées comme confidentielles ou protégées lors de leur divulgation, et qui seront ensuite résumées par écrit et désignées comme confidentielles ou protégées, et communiquées dans les trente jours suivant leur date de divulgation initiale (ci-après les \"Informations Confidentielles\").", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            list = new List(List.UNORDERED);
            list.setIndentationLeft(18);

            item = new ListItem(textLeading, "se trouvaient déjà en la possession de la partie réceptrice avant qu'elles ne lui soient communiquées par la partie divulgatrice, sans que la partie réceptrice ne soit tenue d'une obligation de confidentialité ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "tomberaient dans le domaine public, sans que ce soit en raison d'une faute commise par la partie réceptrice ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "seraient légalement communiquées à la partie réceptrice par un tiers, sans que la partie réceptrice ne soit tenue d'une obligation de confidentialité ;", textFont);
            list.add(item);

            item = new ListItem(textLeading, "seraient développées à titre indépendant par l'une ou l'autre des parties, sans utilisation des Informations Confidentielles.", textFont);
            list.add(item);

            document.add(list);

            paragraph = new Paragraph("Les parties s'engagent à ne pas utiliser les Informations Confidentielles pour leur propre compte ou bénéfice, ou pour le compte ou bénéfice d'un tiers, sauf accord préalable écrit de l'autre partie. Le présent article couvre toutes les Informations Confidentielles qui seront divulguées en cours d'exécution du Contrat. Les obligations mises à la charge des parties à l'égard des Informations Confidentielles expireront deux ans après la date de divulgation de ces Informations.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 11 : FORCE MAJEURE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Aucune des parties ne pourra être tenue pour responsable d'un retard d'exécution, d'une exécution partielle ou d'un défaut d'exécution d'une obligation mise à sa charge par le Contrat qui serait du à un " +
                    "événement présentant les caractéristiques de la force majeure au regard de la jurisprudence des juridictions française (la \"Force Majeure\"), à condition toutefois que la partie concernée notifie rapidement par écrit à l'autre partie l'existence de cette Force Majeure et reprenne ses obligations dès que possible.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 12 : NULLITE PARTIELLE ET TOLERANCE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("La déclaration de nullité ou d'inefficacité d'une quelconque stipulation du Contrat n'entrainera pas de plein droit la nullité ou l'inefficacité des autres stipulations. La tolérance d'une partie aux " +
                    "inexécutions de l'autre partie ne fait pas présumer une future tolérance de la même ou d'autres inexécutions pas plus qu'elle ne caractérise l'intention de l'une ou l'autre des parties de renoncer à faire respecter les droits dont elle dispose au titre du Contrat.", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("ARTICLE 13 : DROIT APPLICABLE / ATTRIBUTION DE COMPETENCE", textBoldFont);
            paragraph.setSpacingBefore(clauseSpacing);
            document.add(paragraph);

            paragraph = new Paragraph("Le Contrat est soumis au droit français. Pour le cas où les parties ne pourraient pas régler leurs différends éventuels à l'amiable, elles conviennent expressément de les soumettre, " +
                    "où que soient les lieux d'exécution des prestations ou les sièges sociaux des parties, à la compétence exclusive du Tribunal de Commerce de Par", textFont);
            paragraph.setLeading(textLeading);
            document.add(paragraph);


            paragraph = new Paragraph("Fait à Paris, le 14 octobre 2011, en deux exemplaires originaux.", textFont);
            paragraph.setSpacingBefore(8);
            paragraph.setSpacingAfter(8);
            document.add(paragraph);


            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            cell = new PdfPCell();

            paragraph = new Paragraph("Pour SUPINFO", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            paragraph = new Paragraph("Olivier COMES", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            paragraph = new Paragraph("Président", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell();

            paragraph = new Paragraph("Pour le Prestataire", textFont);
            paragraph.setLeading(textLeading);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);

            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            document.add(table);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }
}

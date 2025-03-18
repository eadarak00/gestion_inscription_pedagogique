package sn.uasz.m1.inscription.utils;

import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.service.UEService;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PDFExporter {
    private final UEService ueService;

    public PDFExporter() {
        this.ueService = new UEService();
    }

    public void exporterListeEtudiantsUE(UE ue) {
        try {
            Utilisateur user = SessionManager.getUtilisateur();
            ResponsablePedagogique responsable = (ResponsablePedagogique) user;
            List<Etudiant> etudiants = ueService.getEtudiantsByUE(ue.getId());

            // Sélection du fichier à sauvegarder
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Enregistrer le fichier PDF");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers PDF", "pdf"));

            if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return; // Annuler si l'utilisateur ferme la boîte de dialogue
            }

            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Création du document PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // ✅ **1. En-tête avec logo et infos université**
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[] { 2, 5 }); // Répartition des colonnes

            // 📌 **Logo de l'université (à gauche)**
            Image logo = Image.getInstance("src/main/resources/static/img/png/logo_uasz.png");
            logo.scaleToFit(100, 100);
            PdfPCell logoCell = new PdfPCell(logo, false);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(logoCell);

            // 📌 **Infos université + date et responsable dans la même cellule**
            PdfPCell universityCell = new PdfPCell();
            universityCell.setBorder(Rectangle.NO_BORDER);
            universityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Ajout du nom de l'université
            Paragraph universityName = new Paragraph("Université Assane Seck de Ziguinchor",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            universityName.setAlignment(Element.ALIGN_RIGHT); // Alignement à droite
            universityCell.addElement(universityName);

            // Ajout de la faculté et département
            Paragraph department = new Paragraph("Département " + responsable.getDepartement(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            department.setAlignment(Element.ALIGN_RIGHT); // Alignement à droite
            universityCell.addElement(department);

            // Ajout de la date d'exportation
            String dateExport = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            Paragraph dateParagraph = new Paragraph("Date d'exportation : " + dateExport,
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            dateParagraph.setAlignment(Element.ALIGN_RIGHT); // Alignement à droite
            universityCell.addElement(dateParagraph);

            // Ajout du responsable qui exporte
            Paragraph responsableParagraph = new Paragraph(
                    "Exporté par : " + responsable.getPrenom() + " " + responsable.getNom(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            responsableParagraph.setAlignment(Element.ALIGN_RIGHT); // Alignement à droite
            universityCell.addElement(responsableParagraph);

            // Ajout de la cellule dans la table d'en-tête
            headerTable.addCell(universityCell);

            document.add(headerTable);
            document.add(new Paragraph("\n")); // Espace après l'en-tête

            // 3. Séparation visuelle**
            Paragraph separator = new Paragraph(
                "\n*****************************************************\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);

            // 4. Titre du document**
            Paragraph title = new Paragraph(
                    "Liste des étudiants inscrits à l'UE : " + ue.getCode() + " - " + ue.getLibelle(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n")); // Espacement après le titre

            // ✅ **5. Tableau des étudiants**
            PdfPTable table = new PdfPTable(4); // 4 colonnes : INE, Nom, Prénom, Email
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 2, 4, 4, 6 }); // 4 largeurs pour 4 colonnes

            // 📌 En-tête du tableau
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("INE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(new Color(173, 216, 230)); // Bleu clair
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nom", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(new Color(173, 216, 230));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Prénom", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(new Color(173, 216, 230));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Email", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(new Color(173, 216, 230));
            table.addCell(cell);

            // 📌 Ajout des étudiants
            for (Etudiant etudiant : etudiants) {
                table.addCell(etudiant.getIne());
                table.addCell(etudiant.getNom());
                table.addCell(etudiant.getPrenom());
                table.addCell(etudiant.getEmail());
            }

            document.add(table);
            document.close();

            // Popup de succès
            JOptionPane.showMessageDialog(null, "✅ PDF exporté avec succès : " + filePath, "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            // Popup d'erreur
            JOptionPane.showMessageDialog(null, "❌ Erreur lors de l'exportation du PDF : " + e.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

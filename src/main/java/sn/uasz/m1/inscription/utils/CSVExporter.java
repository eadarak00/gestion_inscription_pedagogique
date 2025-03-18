package sn.uasz.m1.inscription.utils;

import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.service.GroupeService;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CSVExporter {
    private final GroupeService groupeService;

    public CSVExporter() {
        this.groupeService = new GroupeService();
    }

    /**
     * Exporte la liste des étudiants d'un groupe de TD en fichier CSV.
     *
     * @param groupe   Le groupe de TD pour lequel exporter la liste.
     * @param filePath Le chemin du fichier CSV à générer.
     */
    public void exportEtudiantsByGroupe(Groupe groupe, String filePath) {
        List<Etudiant> etudiants = groupeService.listerEtudiantsParGroupe(groupe.getId());

        try (FileWriter writer = new FileWriter(filePath)) {
            // Écriture de l'en-tête
            writer.append("INE,Nom,Prenom,Email\n");

            // Écriture des étudiants
            for (Etudiant etudiant : etudiants) {
                writer.append(etudiant.getIne()).append(",")
                      .append(etudiant.getNom()).append(",")
                      .append(etudiant.getPrenom()).append(",")
                      .append(etudiant.getEmail()).append("\n");
            }

            // Pop-up de réussite
            JOptionPane.showMessageDialog(null, "CSV généré avec succès : " + filePath, "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // Pop-up d'erreur en cas d'échec
            JOptionPane.showMessageDialog(null, "Erreur lors de l'écriture du fichier CSV : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erreur lors de l'écriture du fichier CSV", e);
        }
    }

    public void exporterCSV(Groupe groupe) {
        // Créer un JFileChooser pour permettre à l'utilisateur de choisir le dossier de sauvegarde
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir l'emplacement pour enregistrer le fichier CSV");
    
        // Sélectionner le répertoire par défaut
        fileChooser.setCurrentDirectory(new java.io.File(".")); // Optionnel, définit le répertoire de travail actuel
    
        // Ouvrir la boîte de dialogue et vérifier si l'utilisateur a choisi un fichier
        int result = fileChooser.showSaveDialog(null);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtenir le chemin du fichier choisi par l'utilisateur
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
    
            // Récupérer les informations nécessaires pour le nom du fichier
            String formationLibelle = groupe.getFormation().getLibelle(); // Nom de la formation
            String typeGroupe = groupe.getType().name(); // Type du groupe (ex: TD, TP, CM)
            Long idGroupe = groupe.getId(); // ID du groupe
            String dateSuffix = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // Date d'exportation
    
            // Construire le nom du fichier selon le format souhaité
            String fileName = String.format("%s_%s_%s_%d_%s.csv", 
                formationLibelle, 
                "groupe", // Ajoutez "groupe" comme texte statique
                typeGroupe, 
                idGroupe, 
                dateSuffix);
    
            // Combiner le chemin choisi par l'utilisateur avec le nom du fichier
            String filePathWithDate = selectedPath + "_" + fileName;
    
            // Appeler la méthode pour exporter le fichier CSV en passant le groupe
            exportEtudiantsByGroupe(groupe, filePathWithDate);
        } else {
            // Pop-up si l'utilisateur annule l'exportation
            JOptionPane.showMessageDialog(null, "Exportation annulée par l'utilisateur.", "Annulation", JOptionPane.WARNING_MESSAGE);
        }
    }
}
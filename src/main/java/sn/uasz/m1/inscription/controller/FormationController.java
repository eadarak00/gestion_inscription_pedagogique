package sn.uasz.m1.inscription.controller;

import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.service.FormationService;

import java.util.List;

public class FormationController {
    private final FormationService formationService;

    public FormationController() {
        formationService = new FormationService();
    }

    /**
     * Créer une nouvelle formation
     */
    public String ajouterFormation(String libelle, int niveau) {
        if (libelle == null || libelle.isEmpty()) {
            return "Le libelle de la formation est obligatoire.";
        }

        if (niveau <= 0) {
            return "Le libelle de la formation est obligatoire et doit etre superieur a 0.";
        }

        Formation formation = new Formation();
        formation.setLibelle(libelle);
        formation.setNiveau(niveau);

        try {
            formationService.createFormation(formation);
            return "Formation ajoutée avec succès.";
        } catch (Exception e) {
            return "Erreur lors de la création de la formation.";
        }
    }

    /**
     * Récupérer toutes les formations
     */
    public List<Formation> listerFormations() {
        return formationService.getAllFormations();
    }

    /**
     * Récupérer les formations du responsable connecté
     */
    public List<Formation> listerFormationsResponsable() {
        return formationService.getFormationsByResponsable();
    }

    /**
     * Modifier une formation
     */
    public String modifierFormation(Long id, String libelle, int niveau) {
        if (libelle == null || libelle.isEmpty() || niveau <= 0) {
            return "Le libelle et le niveau sont obligatoires.";
        }

        Formation updatedFormation = new Formation();
        updatedFormation.setLibelle(libelle);
        updatedFormation.setNiveau(niveau);

        try {
            formationService.updateFormation(id, updatedFormation);
            return "Formation modifiée avec succès.";
        } catch (Exception e) {
            return "Erreur lors de la modification de la formation.";
        }
    }

    /**
     * Supprimer une formation
     */
    public String supprimerFormation(Long id) {
        try {
            formationService.deleteFormationById(id);
            return "Formation supprimée avec succès.";
        } catch (Exception e) {
            return "Erreur lors de la suppression de la formation.";
        }
    }
}

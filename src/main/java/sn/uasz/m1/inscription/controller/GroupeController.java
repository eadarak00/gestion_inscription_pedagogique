package sn.uasz.m1.inscription.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
import sn.uasz.m1.inscription.service.GroupeService;

public class GroupeController {
    private final GroupeService groupeService;

    public GroupeController() {
        this.groupeService = new GroupeService();
    }

    /**
     * Ajouter un groupe
     */
    public String ajouterGroupe(int capacite, TypeGroupe type, Formation formation) {
        if (capacite <= 0) {
            return "La capacité du groupe est obligatoire et doit être supérieure à 0.";
        }

        if (type == null) {
            return "Le type du groupe est obligatoire.";
        }

        if (formation == null || formation.getId() == null) {
            return "La formation associée est obligatoire et doit être valide.";
        }

        Groupe groupe = new Groupe();
        groupe.setCapacite(capacite);
        groupe.setType(type);
        groupe.setFormation(formation);

        try {
            groupeService.createGroupe(groupe);
            return "Groupe ajouté avec succès.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la création du groupe.";
        }
    }

    /**
     *  Récupérer tous les groupes
     */
    public List<Groupe> listerGroupes() {
        return groupeService.getAllGroupes();
    }

    /**
     * Récupérer les groupes du responsable connecté
     */
    public List<Groupe> listerGroupesResponsable() {
        return groupeService.getGroupesByResponsable();
    }

    /**
     * Modifier un groupe
     */
    public String modifierGroupe(Long id, Groupe groupe) {
        if (groupe.getCapacite() <= 0) {
            return "La capacité du groupe est obligatoire et doit être supérieure à 0.";
        }

        if (groupe.getType() == null) {
            return "Le type du groupe est obligatoire.";
        }

        if (groupe.getFormation() == null || groupe.getFormation().getId() == null) {
            return "La formation associée est obligatoire et doit être valide.";
        }

        try {
            Groupe updatedGroupe = new Groupe();
            updatedGroupe.setCapacite(groupe.getCapacite());
            updatedGroupe.setType(groupe.getType());
            updatedGroupe.setFormation(groupe.getFormation());

            groupeService.updateGroupe(id, updatedGroupe);
            return "Groupe modifié avec succès.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la modification du groupe.";
        }
    }

    /**
     * Supprimer un groupe
     */
    public String supprimerGroupe(Long id) {
        try {
            groupeService.deleteGroupeById(id);
            return "Groupe supprimé avec succès.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la suppression du groupe.";
        }
    }

    /**
     * Trouver un groupe par ID
     */
    public Groupe trouverGroupeParId(Long id) {
        return groupeService.getGroupeById(id);
    }

    /**
     * Récupérer les groupes triés par type
     */
    public List<Groupe> getGroupesTriesParType(boolean ordreCroissant) {
        List<Groupe> groupes = groupeService.getGroupesByResponsable();

        return groupes.stream()
                .sorted(ordreCroissant
                        ? Comparator.comparing(Groupe::getType)
                        : Comparator.comparing(Groupe::getType).reversed())
                .collect(Collectors.toList());
    }
}

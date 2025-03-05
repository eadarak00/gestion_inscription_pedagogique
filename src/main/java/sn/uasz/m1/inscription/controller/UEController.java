package sn.uasz.m1.inscription.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.service.UEService;

public class UEController {
    private final UEService ueService;

    public UEController() {
        this.ueService = new UEService();
    }

    /**
     * Crée un nouvel UE et retourne un message de succès ou d'erreur.
     */
    public String ajouterUE(String code, String libelle, int credit, int coefficient, int volumeHoraire, Enseignant enseignant) {
        try {
            // Vérifications des champs obligatoires
            if (code == null || code.trim().isEmpty() || libelle == null || libelle.trim().isEmpty()) {
                return "Le code et le libellé sont obligatoires.";
            }

            if (!code.startsWith("INF")) {
                return "Le code de l'UE doit commencer par 'INF'.";
            }

            if (credit <= 0) {
                return "Le crédit doit être un entier positif.";
            }

            if (coefficient <= 0) {
                return "Le coefficient doit être un entier positif.";
            }

            if (volumeHoraire <= 0) {
                return "Le volume horaire doit être un entier positif.";
            }

            if (enseignant == null) {
                return "le choix de l'enseignant est obligatoire";
            }

            // Vérification si l'UE existe déjà (optionnel)
            List<UE> existingUEs = ueService.getAllUEs();
            for (UE ueExistante : existingUEs) {
                if (ueExistante.getCode().equalsIgnoreCase(code.trim())) {
                    return "Une UE avec le code '" + code + "' existe déjà.";
                }
            }

            // Création et configuration de l'UE
            UE ue = new UE();
            ue.setCode(code.trim());
            ue.setLibelle(libelle.trim());
            ue.setCredit(credit);
            ue.setCoefficient(coefficient);
            ue.setVolumeHoraire(volumeHoraire);
            ue.setEnseignant(enseignant);

            // Enregistrement de l'UE
            ueService.createUE(ue);

            return " UE '" + code + " - " + libelle + "' ajoutée avec succès !";
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur
            return "Erreur lors de l'ajout de l'UE : " + e.getMessage();
        }
    }

    /**
     * Récupère un UE par son ID.
     */
    public UE trouverUEParId(Long id) {
        try {
            return ueService.getUEById(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Récupère toutes les UEs disponibles.
     */
    public List<UE> listerUEs() {
        return ueService.getUEsByResponsable();
    }

    /**
     * Met à jour un UE existant.
     */
    public String modifierUE(Long id, UE updatingUE) {
        try {
            UE ue = ueService.getUEById(id);
            if (ue == null) {
                return "UE introuvable avec l'ID : " + id;
            }

            ue.setCode(updatingUE.getCode());
            ue.setLibelle(updatingUE.getLibelle());
            ue.setCredit(updatingUE.getCredit());
            ue.setCoefficient(updatingUE.getCoefficient());
            ue.setEnseignant(updatingUE.getEnseignant());

            ueService.updateUE(id, ue);
            return "UE '" + ue.getCode() + "' modifié avec succès !";
        } catch (Exception e) {
            return "Erreur lors de la modification de l'UE : " + e.getMessage();
        }
    }

    /**
     * Supprime un UE par son ID.
     */
    public String supprimerUE(Long id) {
        try {
            ueService.deleteUE(id);
            return "UE supprimé avec succès !";
        } catch (Exception e) {
            return "Erreur lors de la suppression de l'UE : " + e.getMessage();
        }
    }


     public List<UE> getUEsTriesParLibelle(boolean ordreCroissant) {
        List<UE> ues = ueService.getUEsByResponsable();

        return ues.stream()
                .sorted(ordreCroissant
                        ? Comparator.comparing(UE::getLibelle)
                        : Comparator.comparing(UE::getLibelle).reversed())
        
                        .collect(Collectors.toList());
    }



       /**
     * Associe une UE à une formation.
     * @return Message indiquant le succès ou l'échec de l'opération.
     */
    public String associerUEDansFormation(UE ue, Long formationId, boolean obligatoire) {
        if (ue == null || formationId == null) {
            return "L'ID de l'UE et l'ID de la formation sont obligatoires.";
        }
        try {
            ueService.associerUEDansFormation(ue.getId(), formationId, obligatoire);
            return "L'UE a été associée à la formation avec succès.";
        } catch (Exception e) {
            return "Erreur lors de l'association : " + e.getMessage();
        }
    }

    /**
     * Modifie le statut d'une UE (obligatoire ou optionnel).
     * @return Message de confirmation ou d'erreur.
     */
    public String modifierEtatUE(Long ueId, boolean obligatoire) {
        if (ueId == null) {
            return "L'ID de l'UE est obligatoire.";
        }
        try {
            ueService.modifierEtatUE(ueId, obligatoire);
            return "Le statut de l'UE a été modifié avec succès.";
        } catch (Exception e) {
            return "Erreur lors de la modification : " + e.getMessage();
        }
    }

    /**
     * Retire une UE d'une formation si elle est optionnelle.
     * @return Message confirmant le retrait ou expliquant pourquoi ce n'est pas possible.
     */
    public String retirerUEDeFormation(Long ueId) {
        if (ueId == null) {
            return "L'ID de l'UE est obligatoire.";
        }
        try {
            ueService.retirerUEDeFormation(ueId);
            return "L'UE a été retirée de la formation avec succès.";
        } catch (IllegalStateException e) {
            return "Impossible de retirer une UE obligatoire.";
        } catch (Exception e) {
            return "Erreur lors du retrait de l'UE : " + e.getMessage();
        }
    }

    public List<UE> getUEsByFormation(Long formationID){
        return ueService.listerUEsParFormation(formationID);
    }

    public List<UE> getFormationUEsTriesParLibelle(boolean ordreCroissant, Long formationId) {
        List<UE> ues = ueService.listerUEsParFormation(formationId);

        return ues.stream()
                .sorted(ordreCroissant
                        ? Comparator.comparing(UE::getLibelle)
                        : Comparator.comparing(UE::getLibelle).reversed())
        
                        .collect(Collectors.toList());
    }

    public List<UE> listerUEsDisponbles(Long formationId){
        return ueService.listerUesDisponiblePourFormation(formationId);
    }

}

package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

@Slf4j
public class UEService {
    private final UEDAO ueDAO;
    private final ResponsablePedagogiqueService rService;

    public UEService() {
        this.ueDAO = new UEDAO();
        this.rService = new ResponsablePedagogiqueService();

    }

    /**
     * Enregistre un nouvel UE.
     */
    public UE createUE(UE ue) {
        if (ue == null) {
            throw new IllegalArgumentException("L'UE ne peut pas être null.");
        }

        Utilisateur user = SessionManager.getUtilisateur();
        ResponsablePedagogique responsable = rService.getResponsableById(user.getId());
        ue.setResponsable(responsable);
        ue.setFormation(null);
        ue.setObligatoire(true);

        try {
            return ueDAO.save(ue);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'enregistrement de l'UE : " + e.getMessage());
            throw new RuntimeException("Échec de l'enregistrement de l'UE.");
        }
    }

    /**
     * Récupère un UE par son ID.
     */
    public UE getUEById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'UE est invalide.");
        }

        UE ue = ueDAO.findById(id);
        if (ue == null) {
            throw new IllegalArgumentException("Aucun UE trouvé avec l'ID : " + id);
        }
        return ue;
    }

    /**
     * Récupère tous les UEs.
     */
    public List<UE> getAllUEs() {
        return ueDAO.findAll();
    }

    /**
     * Récupère les UEs d'une formation donnée.
     */
    // public List<UE> getUEsByFormation(Formation formation) {
    // if (formation == null || formation.getId() == null) {
    // throw new IllegalArgumentException("La formation est invalide.");
    // }
    // return ueDAO.findByFormation(formation);
    // }

    /**
     * Met à jour un UE.
     */
    public UE updateUE(Long id, UE ue) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'UE est invalide.");
        }
        if (ue == null) {
            throw new IllegalArgumentException("Les nouvelles données de l'UE ne peuvent pas être null.");
        }

        UE existingUE = ueDAO.findById(id);
        if (existingUE == null) {
            throw new IllegalArgumentException("L'UE avec l'ID " + id + " n'existe pas.");
        }

        try {
            return ueDAO.update(id, ue);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'UE : " + e.getMessage());
            throw new RuntimeException("Échec de la mise à jour de l'UE.");
        }
    }

    /**
     * Supprime un UE par son ID.
     */
    public void deleteUE(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'UE est invalide.");
        }

        UE existingUE = ueDAO.findById(id);
        if (existingUE == null) {
            throw new IllegalArgumentException("Impossible de supprimer : aucun UE trouvé avec l'ID " + id);
        }

        try {
            ueDAO.delete(id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'UE : " + e.getMessage());
            throw new RuntimeException("Échec de la suppression de l'UE.");
        }
    }

    public List<UE> getUEsByResponsable() {
        Utilisateur user = SessionManager.getUtilisateur();
        ResponsablePedagogique responsable = rService.getResponsableById(user.getId());
        return ueDAO.findByResponsable(responsable);
    }

    /**
     * Vérifie si une UE est déjà assignée à une formation.
     * 
     * @throws IllegalArgumentException si l'UE est introuvable.
     */
    public boolean estAssignerAUneFormation(Long ueId) {
        UE ue = ueDAO.findById(ueId);
        if (ue == null) {
            throw new IllegalArgumentException("L'UE avec l'ID " + ueId + " est introuvable.");
        }
        return ue.getFormation() != null;
    }

    /**
     * Associe une UE à une formation et définit son statut (obligatoire ou
     * optionnel).
     * 
     * @throws IllegalStateException si l'UE est déjà assignée à une formation.
     */
    public void associerUEDansFormation(Long ueId, Long formationId, boolean obligatoire) {
        if (estAssignerAUneFormation(ueId)) {
            throw new IllegalStateException("L'UE " + ueId + " est déjà assignée à une formation.");
        }

        try {
            ueDAO.associerUEDansFormation(ueId, formationId, obligatoire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'association de l'UE : " + e.getMessage(), e);
        }
    }

    /**
     * Modifie le statut d'une UE (obligatoire ou optionnel).
     * 
     * @throws IllegalArgumentException si l'UE est introuvable.
     */
    public void modifierEtatUE(Long ueId, boolean obligatoire) {
        UE ue = ueDAO.findById(ueId);
        if (ue == null) {
            throw new IllegalArgumentException("UE introuvable avec l'ID " + ueId);
        }

        try {
            ueDAO.modifierEtatUE(ueId, obligatoire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du statut de l'UE : " + e.getMessage(), e);
        }
    }

    /**
     * Retire une UE d'une formation si elle est optionnelle.
     * 
     * @throws IllegalArgumentException si l'UE est introuvable.
     * @throws IllegalStateException    si l'UE est obligatoire et ne peut pas être
     *                                  retirée.
     */
    public void retirerUEDeFormation(Long ueId) {
        UE ue = ueDAO.findById(ueId);
        if (ue == null) {
            throw new IllegalArgumentException("L'UE avec l'ID " + ueId + " est introuvable.");
        }

        if (ue.isObligatoire()) {
            throw new IllegalStateException("Impossible de retirer l'UE " + ueId + " car elle est obligatoire.");
        }

        try {
            ueDAO.retirerUEDeFormation(ueId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du retrait de l'UE : " + e.getMessage(), e);
        }
    }

    /**
     * Liste toutes les UEs d'une formation.
     * 
     */
    public List<UE> listerUEsParFormation(Long formationId) {
        List<UE> ues = ueDAO.findUEsByFormation(formationId);
        return ues;
    }

    public List<UE> listerUesDisponiblePourFormation(Long formationId) {
        // Liste de toutes les UEs disponibles
        List<UE> toutesLesUEs = getAllUEs();
        
        // Liste des UEs déjà associées à la formation donnée
        List<UE> uesDeLaFormation = listerUEsParFormation(formationId);
        
        // Créer une nouvelle liste pour les UEs disponibles
        List<UE> uesDisponibles = new ArrayList<>();
        
        // Itérer sur toutes les UEs et ajouter celles qui ne sont pas déjà dans la formation
        for (UE ue : toutesLesUEs) {
            boolean estDansLaFormation = false;
            
            // Vérifier si l'UE est déjà dans la formation
            for (UE ueFormation : uesDeLaFormation) {
                if (ue.getId().equals(ueFormation.getId())) {
                    estDansLaFormation = true;
                    break;
                }
            }
            
            // Si l'UE n'est pas dans la formation et n'a pas déjà une autre formation associée
            if (!estDansLaFormation && ue.getFormation() == null) {
                uesDisponibles.add(ue);
            }
        }
        
        // Retourner la liste des UEs disponibles
        return uesDisponibles;
    }
    

}

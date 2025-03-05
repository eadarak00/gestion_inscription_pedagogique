package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

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
        ResponsablePedagogique  responsable =  rService.getResponsableById(user.getId());
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
    //     if (formation == null || formation.getId() == null) {
    //         throw new IllegalArgumentException("La formation est invalide.");
    //     }
    //     return ueDAO.findByFormation(formation);
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

     public List<UE> getUEsByResponsable(){
        Utilisateur user = SessionManager.getUtilisateur();
        ResponsablePedagogique responsable = rService.getResponsableById(user.getId());
        return ueDAO.findByResponsable(responsable);
    }
}

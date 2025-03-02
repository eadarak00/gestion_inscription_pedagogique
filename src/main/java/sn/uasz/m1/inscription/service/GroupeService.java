package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.GroupeDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

public class GroupeService {
    private final GroupeDAO groupeDAO;
    private final ResponsablePedagogiqueService rService;

    public GroupeService() {
        groupeDAO = new GroupeDAO();
        rService = new ResponsablePedagogiqueService();
    }

    /**
     * Créer un groupe en base de données.
     */
    public Groupe createGroupe(Groupe groupe) {
        if (groupe == null) {
            throw new IllegalArgumentException("Le groupe ne peut pas être null !");
        }
        return groupeDAO.save(groupe);
    }

    /**
     * Trouver un groupe par son ID.
     */
    public Groupe getGroupeById(Long id) {
        Groupe groupe = groupeDAO.findById(id);
        if (groupe == null) {
            throw new IllegalArgumentException("Aucun groupe trouvé avec l'ID : " + id);
        }
        return groupe;
    }

    /**
     * Récupérer tous les groupes.
     */
    public List<Groupe> getAllGroupes() {
        return groupeDAO.findAll();
    }

    /**
     * Récupérer les groupes du responsable connecté.
     */
    public List<Groupe> getGroupesByResponsable() {
        Utilisateur user = SessionManager.getUtilisateur();

        // Vérifier que l'utilisateur est bien connecté
        if (user == null) {
            throw new IllegalStateException("Aucun utilisateur connecté !");
        }

        // Vérifier que l'utilisateur est bien un responsable pédagogique
        ResponsablePedagogique responsable = rService.getResponsableById(user.getId());
        if (responsable == null) {
            throw new IllegalStateException("Le responsable pédagogique est introuvable !");
        }

        return groupeDAO.findByFormationResponsable(responsable);
    }

    /**
     * Mettre à jour un groupe.
     */
    public Groupe updateGroupe(Long id, Groupe updatedGroupe) {
        if (updatedGroupe == null) {
            throw new IllegalArgumentException("Les nouvelles données du groupe ne peuvent pas être null !");
        }

        // Vérifier si le groupe existe avant modification
        Groupe existingGroupe = groupeDAO.findById(id);
        if (existingGroupe == null) {
            throw new IllegalArgumentException("Aucun groupe trouvé avec l'ID : " + id);
        }

        return groupeDAO.update(id, updatedGroupe);
    }

    /**
     * Supprimer un groupe par son ID.
     */
    public void deleteGroupeById(Long id) {
        try {
            groupeDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du groupe avec ID : " + id, e);
        }
    }

    public List<Groupe> getGroupesByFormation(Formation formation) {
        if (formation == null || formation.getId() == null) {
            throw new IllegalArgumentException("La formation ne peut pas être null et doit avoir un ID valide.");
        }
        return groupeDAO.findByFormationID(formation);
    }
    
}

package sn.uasz.m1.inscription.service;

import java.util.List;
import sn.uasz.m1.inscription.dao.ResponsablePedagogiqueDAO;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Role;
import sn.uasz.m1.inscription.utils.SecurityUtil;

public class ResponsablePedagogiqueService {

    private final ResponsablePedagogiqueDAO responsablePedagogiqueDAO;
    private final RoleService roleService = new RoleService();

    public ResponsablePedagogiqueService() {
        this.responsablePedagogiqueDAO = new ResponsablePedagogiqueDAO();
    }

    /**
     * Enregistre un nouveau Responsable Pédagogique
     */
    public ResponsablePedagogique createResponsable(ResponsablePedagogique responsable) {
        if (responsable == null) {
            throw new IllegalArgumentException("Le responsable ne peut pas être null");
        }

        // Vérification de l'email
        if (!SecurityUtil.verifierEmailResponsable(responsable.getEmail())) {
            throw new IllegalArgumentException("L'email doit être valide et se terminer par @univ-zig.sn");
        }

        // Hashage du mot de passe
        String motDePasseHache = SecurityUtil.hasherMotDePasse(responsable.getMotDePasse());
        responsable.setMotDePasse(motDePasseHache);

        // Vérification du rôle
        Role role = roleService.assignRoleToUtilisateur(responsable);
        responsable.setRole(role);
        
        // Sauvegarder le responsable
        return responsablePedagogiqueDAO.save(responsable);
    }

    /**
     * Récupère un responsable par son ID
     */
    public ResponsablePedagogique getResponsableById(Long id) {
        ResponsablePedagogique responsable = responsablePedagogiqueDAO.findById(id);
        if (responsable == null) {
            throw new IllegalArgumentException("Aucun responsable trouvé avec l'ID : " + id);
        }
        return responsable;
    }

    /**
     * Récupère tous les responsables pédagogiques
     */
    public List<ResponsablePedagogique> getAllResponsables() {
        return responsablePedagogiqueDAO.findAll();
    }

    /**
     * Met à jour les informations d’un responsable
     */
    public ResponsablePedagogique updateResponsable(Long id, ResponsablePedagogique updatedResponsable) {
        if (updatedResponsable == null) {
            throw new IllegalArgumentException("Les nouvelles données du responsable ne peuvent pas être null");
        }

        if (!SecurityUtil.verifierEmailResponsable(updatedResponsable.getEmail())) {
            throw new IllegalArgumentException("L'email doit être valide et se terminer par @univ-zig.sn");
        }

        if (updatedResponsable.getMotDePasse().isEmpty()) {
            updatedResponsable.setMotDePasse(getResponsableById(id).getMotDePasse());
        }

        // Hashage du mot de passe
        String motDePasseHache = SecurityUtil.hasherMotDePasse(updatedResponsable.getMotDePasse());
        updatedResponsable.setMotDePasse(motDePasseHache);



        return responsablePedagogiqueDAO.update(id, updatedResponsable);
    }

    /**
     * Supprime un responsable pédagogique par son ID
     */
    public void deleteResponsable(Long id) {
        responsablePedagogiqueDAO.delete(id);
    }
}

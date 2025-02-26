package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.EtudiantDAO;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Role;
import sn.uasz.m1.inscription.utils.SecurityUtil;

public class EtudiantService {
    private final EtudiantDAO etudiantDAO;
    private final RoleService roleService;

    public EtudiantService() {
        this.etudiantDAO = new EtudiantDAO();
        this.roleService = new RoleService();
    }

    /**
     * Enregistre un nouveau Etudiant
     */
    public Etudiant createEtudiant(Etudiant etudiant) {
        if (etudiant == null) {
            throw new IllegalArgumentException("Le etudiant ne peut pas être null");
        }

        // Vérification de l'email
        if (!SecurityUtil.verifierEmailEtudiant(etudiant.getEmail())) {
            throw new IllegalArgumentException("L'email doit être valide et se terminer par @univ-zig.sn");
        }

        // Hashage du mot de passe
        String motDePasseHache = SecurityUtil.hasherMotDePasse(etudiant.getMotDePasse());
        etudiant.setMotDePasse(motDePasseHache);

        // Vérification du rôle
        Role role = roleService.assignRoleToUtilisateur(etudiant);
        etudiant.setRole(role);
        
        // Sauvegarder le etudiant
        return etudiantDAO.save(etudiant);
    }

    /**
     * Récupère un etudiant par son ID
     */
    public Etudiant getEtudiantById(Long id) {
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant == null) {
            throw new IllegalArgumentException("Aucun etudiant trouvé avec l'ID : " + id);
        }
        return etudiant;
    }

    /**
     * Récupère tous les etudiants pédagogiques
     */
    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.findAll();
    }

    /**
     * Met à jour les informations d’un etudiant
     */
    public Etudiant updateEtudiant(Long id, Etudiant updatedEtudiant) {
        if (updatedEtudiant == null) {
            throw new IllegalArgumentException("Les nouvelles données du etudiant ne peuvent pas être null");
        }
        return etudiantDAO.update(id, updatedEtudiant);
    }

    /**
     * Supprime un etudiant pédagogique par son ID
     */
    public void deleteEtudiant(Long id) {
        etudiantDAO.delete(id);
    }
}

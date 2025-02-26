package sn.uasz.m1.inscription.service;


import sn.uasz.m1.inscription.dao.RoleDAO;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Role;
import sn.uasz.m1.inscription.model.Utilisateur;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleService {
    private final RoleDAO roleDAO = new RoleDAO();

    /**
     * Ajoute un nouveau rôle s'il n'existe pas déjà.
     * @param libelle Libellé du rôle (ex: "ETUDIANT")
     * @return true si le rôle a été ajouté, false s'il existe déjà
     */
    public boolean ajouterRole(String libelle) {
        if (libelle == null || libelle.trim().isEmpty()) {
            log.info("Libellé invalide.");
            return false;
        }

        if (roleDAO.trouverParLibelle(libelle) != null) {
            log.info("Rôle déjà existant : " + libelle);
            return false;
        }

        Role role = new Role(null, libelle);
        roleDAO.ajouter(role);
        log.info("Rôle ajouté : " + libelle);
        return true;
    }

    /**
     * Récupère un rôle par son ID.
     * @param id ID du rôle
     * @return Le rôle ou null s'il n'existe pas
     */
    public Role trouverParId(Long id) {
        return roleDAO.trouverParId(id);
    }

    /**
     * Récupère un rôle par son libellé.
     * @param libelle Nom du rôle
     * @return Le rôle ou null s'il n'existe pas
     */
    public Role trouverParLibelle(String libelle) {
        return roleDAO.trouverParLibelle(libelle);
    }

    /**
     * Liste tous les rôles.
     * @return Liste des rôles disponibles
     */
    public List<Role> listerRoles() {
        return roleDAO.listerTous();
    }

    /**
     * Supprime un rôle uniquement s'il existe.
     * @param id ID du rôle à supprimer
     * @return true si suppression réussie, false si le rôle n'existe pas
     */
    public boolean supprimerRole(Long id) {
        Role role = roleDAO.trouverParId(id);
        if (role == null) {
            log.info("Rôle introuvable avec ID : " + id);
            return false;
        }

        roleDAO.supprimer(id);
        log.info("Rôle supprimé : " + role.getLibelle());
        return true;
    }

    /**
     * Assigner un role de maniere automatique
    */
    public Role assignRoleToUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof ResponsablePedagogique) {
            return roleDAO.trouverParLibelle("RESPONSABLE");
        } 
        // else if (utilisateur instanceof Etudiant) {
        //     return roleDAO.trouverParLibelle("ETUDIANT");
        // }
         else {
            throw new IllegalArgumentException("Rôle non défini pour cet utilisateur.");
        }
    }
}

package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.EnseignantDAO;
import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.utils.SecurityUtil;

public class EnseignantService {
    private final EnseignantDAO enseignantDAO;

    public EnseignantService() {
        this.enseignantDAO = new EnseignantDAO();
    }

    /**
     * Enregistre un nouvel enseignant
     */
    public Enseignant createEnseignant(Enseignant enseignant) {
        if (enseignant == null) {
            throw new IllegalArgumentException("L'enseignant ne peut pas être null.");
        }

        // Vérification de l'email
        if (enseignant.getEmail() == null || !SecurityUtil.verifierEmailResponsable(enseignant.getEmail())) {
            throw new IllegalArgumentException("L'email doit être valide et se terminer par @univ-zig.sn.");
        }

        // Sauvegarde de l'enseignant
        try {
            return enseignantDAO.save(enseignant);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'enregistrement de l'enseignant : " + e.getMessage());
            throw new RuntimeException("Échec de l'enregistrement de l'enseignant.");
        }
    }

    /**
     * Récupère un enseignant par son ID
     */
    public Enseignant getEnseignantById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'enseignant est invalide.");
        }

        Enseignant enseignant = enseignantDAO.findById(id);
        if (enseignant == null) {
            throw new IllegalArgumentException("Aucun enseignant trouvé avec l'ID : " + id);
        }
        return enseignant;
    }

    /**
     * Récupère tous les enseignants
     */
    public List<Enseignant> getAllEnseignants() {
        return enseignantDAO.findAll();
    }

    /**
     * Met à jour un enseignant
     */
    public Enseignant updateEnseignant(Long id, Enseignant enseignant) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'enseignant est invalide.");
        }
        if (enseignant == null) {
            throw new IllegalArgumentException("Les nouvelles données de l'enseignant ne peuvent pas être null.");
        }

        try {
            return enseignantDAO.update(id, enseignant);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'enseignant : " + e.getMessage());
            throw new RuntimeException("Échec de la mise à jour de l'enseignant.");
        }
    }

    /**
     * Supprime un enseignant par son ID
     */
    public void deleteEnseignant(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'enseignant est invalide.");
        }

        try {
            enseignantDAO.delete(id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'enseignant : " + e.getMessage());
            throw new RuntimeException("Échec de la suppression de l'enseignant.");
        }
    }

    public Enseignant getByEmail(String email){
        if (email == null || email.isEmpty()){
            new IllegalArgumentException("Email invalide !!");
        }
        return enseignantDAO.findByEmail(email);
    }
}

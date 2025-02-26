package sn.uasz.m1.inscription.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class EtudiantDAO implements IDAO<Etudiant> {
     @Override
    public Etudiant save(Etudiant o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
            return o;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde", e);
        }
    }


    @Override
    public Etudiant findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Etudiant.class, id);
        }
    }

    @Override
    public List<Etudiant> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT r FROM Etudiant r", Etudiant.class)
                    .getResultList();
        }
    }

    @Override
    public Etudiant update(Long id, Etudiant o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            
            // Vérification de l'existence
            Etudiant existing = entityManager.find(Etudiant.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Etudiant trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setPrenom(o.getPrenom());
            existing.setNom(o.getNom());
            existing.setEmail(o.getEmail());
            existing.setMotDePasse(o.getMotDePasse());
            existing.setIne(o.getIne());
            existing.setDateNaissance(o.getDateNaissance());
            existing.setSexe(o.getSexe());
            existing.setAdresse(o.getAdresse());
            
            Etudiant updated = entityManager.merge(existing);
            entityManager.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            Etudiant responsable = entityManager.find(Etudiant.class, id);
            if (responsable == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Etudiant trouvé avec l'ID : " + id);
            }
            entityManager.remove(responsable);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }
}

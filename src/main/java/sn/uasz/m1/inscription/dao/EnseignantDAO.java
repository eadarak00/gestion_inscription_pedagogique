package sn.uasz.m1.inscription.dao;

import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class EnseignantDAO implements IDAO<Enseignant> {

    @Override
    public Enseignant save(Enseignant o) {
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
    public Enseignant findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Enseignant.class, id);
        }
    }

    @Override
    public List<Enseignant> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT e FROM Enseignant e", Enseignant.class)
                    .getResultList();
        }
    }

    @Override
    public Enseignant update(Long id, Enseignant o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Vérification de l'existence
            Enseignant existing = entityManager.find(Enseignant.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Enseignant trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setNom(o.getNom());
            existing.setPrenom(o.getPrenom());
            existing.setEmail(o.getEmail());
            existing.setSpecialite(o.getSpecialite());

            Enseignant updated = entityManager.merge(existing);
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
            Enseignant enseignant = entityManager.find(Enseignant.class, id);
            if (enseignant == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Enseignant trouvé avec l'ID : " + id);
            }
            entityManager.remove(enseignant);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

    public Enseignant findByEmail(String email) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            TypedQuery<Enseignant> query = entityManager.createQuery(
                "SELECT e FROM Enseignant e WHERE e.email = :email", Enseignant.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

package sn.uasz.m1.inscription.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class FormationDAO implements IDAO<Formation> {

    @Override
    public Formation save(Formation o) {
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
    public Formation findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Formation.class, id);
        }
    }

    @Override
    public List<Formation> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT f FROM Formation f", Formation.class)
                    .getResultList();
        }
    }

    public List<Formation> findByResponsable(ResponsablePedagogique responsable) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT f FROM Formation f WHERE f.responsable = :responsable", Formation.class)
                    .setParameter("responsable", responsable)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Formation findByLibelle(String libelle) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            // Query to fetch Formation by libelle
            String query = "SELECT f FROM Formation f WHERE f.libelle = :libelle";
            TypedQuery<Formation> typedQuery = entityManager.createQuery(query, Formation.class);
            typedQuery.setParameter("libelle", libelle);

            // Get single result (will throw NoResultException if no result is found)
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            // Handle case when no result is found (e.g., return null or custom response)
            return null;
        } catch (Exception e) {
            // Log the exception or rethrow it as a custom exception
            e.printStackTrace(); // Consider logging this for better visibility
            throw new RuntimeException("An error occurred while retrieving Formation by libelle", e);
        }
    }

    

    @Override
    public Formation update(Long id, Formation o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Vérification de l'existence
            Formation existing = entityManager.find(Formation.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucune Formation trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setLibelle(o.getLibelle());
            existing.setNiveau(o.getNiveau());

            Formation updated = entityManager.merge(existing);
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
            Formation formation = entityManager.find(Formation.class, id);
            if (formation == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucune Formation trouvé avec l'ID : " + id);
            }
            entityManager.remove(formation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

}

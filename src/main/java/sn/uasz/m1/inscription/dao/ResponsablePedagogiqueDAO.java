package sn.uasz.m1.inscription.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class ResponsablePedagogiqueDAO implements IDAO<ResponsablePedagogique> {


    @Override
    public ResponsablePedagogique save(ResponsablePedagogique o) {
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
    public ResponsablePedagogique findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(ResponsablePedagogique.class, id);
        }
    }

    @Override
    public List<ResponsablePedagogique> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT r FROM ResponsablePedagogique r", ResponsablePedagogique.class)
                    .getResultList();
        }
    }

    @Override
    public ResponsablePedagogique update(Long id, ResponsablePedagogique o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            
            // Vérification de l'existence
            ResponsablePedagogique existing = entityManager.find(ResponsablePedagogique.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun ResponsablePedagogique trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setPrenom(o.getPrenom());
            existing.setNom(o.getNom());
            existing.setEmail(o.getEmail());
            existing.setMotDePasse(o.getMotDePasse());
            existing.setDepartement(o.getDepartement());

            ResponsablePedagogique updated = entityManager.merge(existing);
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
            ResponsablePedagogique responsable = entityManager.find(ResponsablePedagogique.class, id);
            if (responsable == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun ResponsablePedagogique trouvé avec l'ID : " + id);
            }
            entityManager.remove(responsable);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }
}

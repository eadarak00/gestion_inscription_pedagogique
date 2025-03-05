package sn.uasz.m1.inscription.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class UEDAO implements IDAO<UE> {

    @Override
    public UE save(UE o) {
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
    public UE findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(UE.class, id);
        }
    }

    @Override
    public List<UE> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT e FROM UE e", UE.class)
                    .getResultList();
        }
    }

    @Override
    public UE update(Long id, UE o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Vérification de l'existence
            UE existing = entityManager.find(UE.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucune UE trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setCode(o.getCode());
            existing.setCoefficient(o.getCoefficient());
            existing.setCredit(o.getCredit());
            existing.setLibelle(o.getLibelle());
            existing.setEnseignant(o.getEnseignant());

            UE updated = entityManager.merge(existing);
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
            UE enseignant = entityManager.find(UE.class, id);
            if (enseignant == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun UE trouvé avec l'ID : " + id);
            }
            entityManager.remove(enseignant);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

    public List<UE> findByResponsable(ResponsablePedagogique responsable) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT e FROM UE e WHERE e.responsable = :responsable", UE.class)
                    .setParameter("responsable", responsable)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

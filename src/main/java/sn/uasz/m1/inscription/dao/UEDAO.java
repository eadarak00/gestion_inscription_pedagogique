package sn.uasz.m1.inscription.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

     /**
     * Associe une UE à une formation et définit si elle est obligatoire ou optionnelle.
     */
    public void associerUEDansFormation(Long ueId, Long formationId, boolean obligatoire) {
        try (EntityManager em = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {
                UE ue = em.find(UE.class, ueId);
                Formation formation = em.find(Formation.class, formationId);

                if (ue == null || formation == null) {
                    throw new IllegalArgumentException("UE ou formation introuvable.");
                }

                ue.setFormation(formation);
                ue.setObligatoire(obligatoire);
                em.merge(ue);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * Modifie l'état d'une UE dans une formation (optionnelle ou obligatoire).
     */
    public void modifierEtatUE(Long ueId, boolean obligatoire) {
        try (EntityManager em = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {
                UE ue = em.find(UE.class, ueId);
                if (ue == null) {
                    throw new IllegalArgumentException("UE introuvable.");
                }

                ue.setObligatoire(obligatoire);
                em.merge(ue);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * Retire une UE d'une formation si elle est optionnelle.
     */
    public boolean retirerUEDeFormation(Long ueId) {
        try (EntityManager em = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {
                UE ue = em.find(UE.class, ueId);
                if (ue == null) {
                    throw new IllegalArgumentException("UE introuvable.");
                }

                if (ue.isObligatoire()) {
                    return false; // L'UE est obligatoire, donc elle ne peut pas être retirée.
                }

                ue.setFormation(null);
                em.merge(ue);

                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * Récupère toutes les UEs d'une formation.
     */
    public List<UE> findUEsByFormation(Long formationId) {
        try (EntityManager em = DatabaseUtil.getEntityManager()) {
            return em.createQuery("SELECT u FROM UE u WHERE u.formation.id = :formationId", UE.class)
                    .setParameter("formationId", formationId)
                    .getResultList();
        }
    }
}

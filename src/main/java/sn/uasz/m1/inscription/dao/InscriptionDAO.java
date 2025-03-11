package sn.uasz.m1.inscription.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import sn.uasz.m1.inscription.model.Inscription;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

import java.util.List;

public class InscriptionDAO {

    /**
     * Enregistre une inscription pédagogique en base de données.
     */
    public void save(Inscription inscription) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            
            try {
                entityManager.persist(inscription);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Erreur lors de l'enregistrement de l'inscription pédagogique", e);
            }
        } 
    }

    /**
     * Récupère les inscriptions pédagogiques d'un étudiant.
     */
    public List<Inscription> findByEtudiant(Long etudiantId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT i FROM InscriptionPedagogique i WHERE i.etudiant.id = :etudiantId", 
                    Inscription.class)
                .setParameter("etudiantId", etudiantId)
                .getResultList();
        }
    }
}


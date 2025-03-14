package sn.uasz.m1.inscription.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.m1.inscription.model.Inscription;
import sn.uasz.m1.inscription.model.enumeration.StatutInscription;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

import java.util.List;

@Slf4j
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

    public boolean isInscriptionExistante(Long etudiantId, Long formationId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            // Vérifier si une inscription validée existe pour cet étudiant et cette formation
            Long count = entityManager.createQuery(
                    "SELECT count(i) FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.formation.id = :formationId AND i.statut = :statut", Long.class)
                    .setParameter("etudiantId", etudiantId)
                    .setParameter("formationId", formationId)
                    .setParameter("statut", StatutInscription.ACCEPTEE)
                    .getSingleResult();
            
            return count > 0; 
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();
            return false;  // Par défaut, retourner false si une erreur survient
        }
    }

    public List<Inscription> findByResponsable(Long responsableId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT i FROM Inscription i " +
                    "WHERE i.formation.responsable.id = :responsableId", 
                    Inscription.class)
                .setParameter("responsableId", responsableId)
                .getResultList();
        }
    }
    
    

    
}


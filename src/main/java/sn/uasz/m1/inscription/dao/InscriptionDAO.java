package sn.uasz.m1.inscription.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.m1.inscription.model.Etudiant;
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

    public boolean isInscriptionExistanteByFormation(Long etudiantId, Long formationId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            // Vérifier si une inscription validée existe pour cet étudiant et cette formation
            Long count = entityManager.createQuery(
                    "SELECT count(i) FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.formation.id = :formationId", Long.class)
                    .setParameter("etudiantId", etudiantId)
                    .setParameter("formationId", formationId)
                    .getSingleResult();
            
            return count > 0; 
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();
            return false;  // Par défaut, retourner false si une erreur survient
        }
    }

    public boolean isInscriptionExistante(Long etudiantId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            // Vérifier si une inscription validée existe pour cet étudiant et cette formation
            Long count = entityManager.createQuery(
                    "SELECT count(i) FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.statut = :statut", Long.class)
                    .setParameter("etudiantId", etudiantId)
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

    public List<Inscription> findPendingByResponsable(Long responsableId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT i FROM Inscription i " +
                    "WHERE i.formation.responsable.id = :responsableId " +
                    "AND i.statut = :statut", Inscription.class)
                .setParameter("responsableId", responsableId)
                .setParameter("statut", StatutInscription.EN_ATTENTE)
                .getResultList();
        }
    }

    public List<Etudiant> findEtudiantsByFormation(Long formationId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT i.etudiant FROM Inscription i " +
                            "WHERE i.formation.id = :formationId " +
                            "AND i.statut = :statut",
                    Etudiant.class)
                    .setParameter("formationId", formationId)
                    .setParameter("statut", StatutInscription.ACCEPTEE) 
                    .getResultList();
        }
    }

    public List<Etudiant> findEtudiantsByUE(Long ueId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT DISTINCT i.etudiant FROM Inscription i " +
                    "JOIN i.ues u WHERE u.id = :ueId AND i.statut = :statut", Etudiant.class)
                    .setParameter("ueId", ueId)
                    .setParameter("statut", StatutInscription.ACCEPTEE)
                    .getResultList();
        }
    }
    
    
    public void refuserInscription(Long inscriptionId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            
            try {
                Inscription inscription = entityManager.find(Inscription.class, inscriptionId);
                if (inscription == null) {
                    throw new IllegalArgumentException("⚠ L'inscription avec ID " + inscriptionId + " n'existe pas.");
                }
    
                inscription.setStatut(StatutInscription.REFUSEE); 
                entityManager.merge(inscription); 
    
                transaction.commit();
                System.out.println("Inscription ID " + inscriptionId + " refusée avec succès.");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Erreur lors du refus de l'inscription : " + e.getMessage(), e);
            }
        }
    }

    public void accepterInscription(Long inscriptionId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            
            try {
                Inscription inscription = entityManager.find(Inscription.class, inscriptionId);
                if (inscription == null) {
                    throw new IllegalArgumentException("L'inscription avec ID " + inscriptionId + " n'existe pas.");
                }
    
                inscription.setStatut(StatutInscription.ACCEPTEE); 
                entityManager.merge(inscription); 
    
                transaction.commit();
                System.out.println("Inscription ID " + inscriptionId + " acceptee avec succès.");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Erreur lors du refus de l'inscription : " + e.getMessage(), e);
            }
        }
    }


    public Inscription findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Inscription.class, id);
        }
    }
    

    public void update(Inscription inscription) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            try {
                entityManager.merge(inscription);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Erreur lors de la mise à jour de l'inscription.", e);
            }
        }
    }
    
    
}


package sn.uasz.m1.inscription.dao;


import sn.uasz.m1.inscription.model.Role;
import sn.uasz.m1.inscription.utils.DatabaseUtil;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class RoleDAO {

    /**
     * Ajoute un rôle dans la base de données.
     * @param role Rôle à ajouter
     */
    public void ajouter(Role role) {
        EntityManager em = DatabaseUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(role);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Trouve un rôle par son ID.
     * @param id ID du rôle
     * @return Le rôle trouvé ou null si inexistant
     */
    public Role trouverParId(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            return em.find(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Trouve un rôle par son libellé.
     * @param libelle Nom du rôle (ex: "ETUDIANT")
     * @return Le rôle trouvé ou null si inexistant
     */
    public Role trouverParLibelle(String libelle) {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.libelle = :libelle", Role.class);
            query.setParameter("libelle", libelle);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun rôle trouvé pour : " + libelle);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Liste tous les rôles dans la base de données.
     * @return Liste des rôles
     */
    public List<Role> listerTous() {
        EntityManager em = DatabaseUtil.getEntityManager();
        try {
            TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Supprime un rôle par son ID.
     * @param id ID du rôle à supprimer
     */
    public void supprimer(Long id) {
        EntityManager em = DatabaseUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, id);
            if (role != null) {
                em.remove(role);
            } else {
                System.out.println("⚠️ Aucun rôle à supprimer avec l'ID : " + id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
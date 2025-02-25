package sn.uasz.m1.inscription.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class UtilisateurDAO {
    public Utilisateur trouverParEmail(String email) {
        try (EntityManager em = DatabaseUtil.getEntityManagerFactory().createEntityManager()) {
            try {
                TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email",
                        Utilisateur.class);
                query.setParameter("email", email);
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }

    }
}

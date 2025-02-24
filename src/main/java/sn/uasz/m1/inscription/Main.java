package sn.uasz.m1.inscription;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import sn.uasz.m1.inscription.model.Test;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class Main {
    // public static void main(String[] args) {
    //     System.out.println("Hello world!");
    // }

    public static void main(String[] args) {
        

        // Test de la connexion en effectuant une opération simple
     
        try (EntityManager em = DatabaseUtil.getEntityManagerFactory().createEntityManager()) {
                EntityTransaction tx = em.getTransaction();
                try {
                    tx.begin();
                    Test user = new Test();
                    user.setId(1L);
                    user.setName("John Doe");
                    em.persist(user);
                    tx.commit();
                    System.out.println("Utilisateur sauvegardé avec succès !");
             
                   
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                System.err.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
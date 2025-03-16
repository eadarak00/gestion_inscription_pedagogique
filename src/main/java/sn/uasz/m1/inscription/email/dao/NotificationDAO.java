package sn.uasz.m1.inscription.email.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream.LongMapMultiConsumer;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.email.model.Notification;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class NotificationDAO {

    public Notification saveNotification(Notification notification) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(notification);
            entityManager.getTransaction().commit();
            return notification;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la notification", e);
        }
    }

    public Notification findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Notification.class, id);
        }
    }

    public List<Notification> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT n FROM Notification n", Notification.class)
                    .getResultList();
        }
    }

    public List<Notification> findByDestinataire(String destinataire) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT n FROM Notification n WHERE n.destinataire = :destinataire", Notification.class)
                    .setParameter("destinataire", destinataire)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int unRead(String destinataire) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            // Compter les notifications non lues pour le destinataire donné
            Long count = entityManager.createQuery(
                    "SELECT count(n) FROM Notification n WHERE n.destinataire = :destinataire AND n.lue = false", Long.class)
                    .setParameter("destinataire", destinataire)
                    .getSingleResult();
            
            return count.intValue(); 
        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // En cas d'erreur, retourner 0
        }
    }
    

    public void markAsRead(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Vérification de l'existence
            Notification notification = entityManager.find(Notification.class, id);
            if (notification == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucune Notification trouvée avec l'ID : " + id);
            }

            // Mise à jour des champs
            notification.setLue(true);

            entityManager.merge(notification);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }

    public void delete(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            Notification notification = entityManager.find(Notification.class, id);
            if (notification == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucune Notification trouvé avec l'ID : " + id);
            }
            entityManager.remove(notification);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }
}

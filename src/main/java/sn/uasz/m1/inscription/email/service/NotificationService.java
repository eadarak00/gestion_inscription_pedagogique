package sn.uasz.m1.inscription.email.service;

import java.time.LocalDate;
import java.util.List;

import sn.uasz.m1.inscription.email.dao.NotificationDAO;
import sn.uasz.m1.inscription.email.model.Notification;
import sn.uasz.m1.inscription.model.Etudiant;

public class NotificationService {
     private NotificationDAO notificationDAO;

    // Constructeur pour injecter le NotificationDAO
    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    public void ajouterNotification(String destinataire, String titre, String message) {
        // Créer un objet Notification
        Notification notification = new Notification();
        notification.setDestinataire(destinataire);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDate.now());
        
        // Sauvegarder la notification dans la base de données via le DAO
        notificationDAO.saveNotification(notification);
    }

    public List<Notification> recupererNotifications(String destinataire) {
        return notificationDAO.findByDestinataire(destinataire);
    }

    public int recupererNotificationsNonLues(String destinataire) {
        return notificationDAO.unRead(destinataire);
    }

    // Marquer une notification comme lue
    public void marquerNotificationCommeLue(Long notificationId) {
        notificationDAO.markAsRead(notificationId);
    }

    public void notifierResponsableInscription(String destinataireResponsable, String formationLibelle, Etudiant etudiant) {
        // Créer le message de notification
        String titre = "Nouvelle inscription à la formation " + formationLibelle;
        String message = "L'étudiant " + etudiant.getPrenom() + " " + etudiant.getNom() + " (INE: " + etudiant.getIne() + ") vient de s'inscrire à la formation : " + formationLibelle;

        // Ajouter la notification
        ajouterNotification(destinataireResponsable, titre, message);
    }

    public void notifierEtudiantInscription(String destinataireEtudiant, String formationLibelle) {
        // Créer le message de notification
        String titre = "Confirmation d'inscription";
        String message = "Vous êtes inscrit avec succès à la formation : " + formationLibelle;

        // Ajouter la notification
        ajouterNotification(destinataireEtudiant, titre, message);
    }

    public void notifierEtudiantInscriptionRefuser(String destinataireEtudiant, String formationLibelle) {
        // Créer le message de notification
        String titre = "Inscription refusée";
        String message = "Votre demande d'inscription à la formation " + formationLibelle + " a été refusée.";
    
        // Ajouter la notification
        ajouterNotification(destinataireEtudiant, titre, message);
    }
    
}


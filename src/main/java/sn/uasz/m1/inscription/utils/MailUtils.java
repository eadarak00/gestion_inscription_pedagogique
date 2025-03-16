package sn.uasz.m1.inscription.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtils {
    private static final String SMTP_HOST = "smtp.gmail.com";  // Modifie si besoin
    private static final String SMTP_PORT = "587";            // 465 pour SSL ou 587 pour TLS
    private static final String USERNAME = "drameelabdou@gmail.com";  // Ton email
    private static final String PASSWORD = "xgdk obqv nlhb nvlb";   // Ton mot de passe ou App Password

    /**
     * Envoie un email.
     * @param destinataire Adresse email du destinataire
     * @param sujet Sujet de l'email
     * @param messageTexte Contenu de l'email (peut être du HTML)
     */
    // public static void envoyerEmail(String destinataire, String sujet, String messageTexte) {
    //     Properties props = new Properties();
    //     props.put("mail.smtp.host", SMTP_HOST);
    //     props.put("mail.smtp.port", SMTP_PORT);
    //     props.put("mail.smtp.auth", "true");
    //     props.put("mail.smtp.starttls.enable", "true"); 

    //     // Création de la session avec authentification
    //     Session session = Session.getInstance(props, new Authenticator() {
    //         @Override
    //         protected PasswordAuthentication getPasswordAuthentication() {
    //             return new PasswordAuthentication(USERNAME, PASSWORD);
    //         }
    //     });

    //     try {
    //         // Création du message
    //         Message message = new MimeMessage(session);
    //         message.setFrom(new InternetAddress(USERNAME));
    //         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
    //         message.setSubject(sujet);
    //         message.setContent(messageTexte, "text/html; charset=utf-8");  // Permet d'envoyer du HTML

    //         // Envoi du message
    //         Transport.send(message);
    //         System.out.println("Email envoyé à " + destinataire);
    //     } catch (MessagingException e) {
    //         throw new RuntimeException("Erreur lors de l'envoi de l'email.", e);
    //     }
    // }

    public static void envoyerEmail(String destinataire, String sujet, String messageTexte) {
    Properties props = new Properties();
    props.put("mail.smtp.host", SMTP_HOST);
    props.put("mail.smtp.port", SMTP_PORT);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // Création de la session avec authentification
    Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(USERNAME, PASSWORD);
        }
    });

    try {
        // Création du message
        Message message = new MimeMessage(session);
        
        // Utilisation de "Service Pédagogique" comme nom d'affichage
        message.setFrom(new InternetAddress(USERNAME, "Service Pédagogique de l'UASZ"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
        message.setSubject(sujet);
        message.setContent(messageTexte, "text/html; charset=utf-8");  

        // Envoi du message
        Transport.send(message);
        System.out.println("Email envoyé à " + destinataire);
    } catch (MessagingException | UnsupportedEncodingException e) {
        throw new RuntimeException("Erreur lors de l'envoi de l'email.", e);
    }
}


}

package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import sn.uasz.m1.inscription.dao.InscriptionDAO;
import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.email.service.NotificationService;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Inscription;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.model.enumeration.StatutInscription;
import sn.uasz.m1.inscription.utils.MailUtils;
import sn.uasz.m1.inscription.utils.SessionManager;

public class InscriptionService {
    private final InscriptionDAO inscriptionDAO;
    private final UEDAO ueDAO;
    private final EtudiantService etudiantService;
    private final FormationService formationService;
    private final NotificationService notificationService;

    public InscriptionService() {
        this.inscriptionDAO = new InscriptionDAO();
        this.ueDAO = new UEDAO();
        this.etudiantService = new EtudiantService();
        this.formationService = new FormationService();
        this.notificationService = new NotificationService();

    }

    public void inscrireEtudiant(Long formationId, List<UE> ueChoisies) {
        try {
            // Récupérer l'utilisateur connecté
            Utilisateur connectedUser = SessionManager.getUtilisateur();
            Etudiant etudiant = etudiantService.getEtudiantById(connectedUser.getId());
            if (etudiant == null) {
                throw new RuntimeException("Étudiant non trouvé.");
            }

            // Vérifier que la formation existe
            Formation formation = formationService.getFormationById(formationId);
            if (formation == null) {
                throw new IllegalArgumentException("Formation non trouvée.");
            }

            // Vérifier si l'étudiant est déjà inscrit à cette formation
            boolean inscriptionExistante = inscriptionDAO.isInscriptionExistanteByFormation(etudiant.getId(),
                    formationId);
            if (inscriptionExistante) {
                throw new RuntimeException("L'étudiant a déjà une inscription à cette formation.");
            }

            boolean isInscription = inscriptionDAO.isInscriptionExistante(etudiant.getId());
            if (isInscription) {
                throw new RuntimeException("L'étudiant est déjà inscrit à une formation.");
            }

            // Récupérer le responsable pédagogique de la formation
            ResponsablePedagogique responsable = formationService.getResponsablePedagogique(formationId);

            // Récupérer toutes les UEs optionnelles disponibles pour cette formation
            List<UE> uesOptionnellesDisponibles = formationService.getOptionalUEs(formationId);

            // Si la formation n'a pas d'UEs optionnelles
            if (uesOptionnellesDisponibles.isEmpty()) {
                System.out.println(
                        "Cette formation n'a aucune UE optionnelle. L'inscription se fait sans sélection d'UEs.");

                // Créer une inscription sans UEs optionnelles
                Inscription inscription = new Inscription();
                inscription.setEtudiant(etudiant);
                inscription.setFormation(formation);
                inscription.setUesOptionnelles(new ArrayList<>());
                inscription.setUes(null);
                inscriptionDAO.save(inscription);

                // Notifier le responsable de l'inscription de l'étudiant
                notificationService.notifierResponsableInscription(responsable.getEmail(), formation.getLibelle(),
                        etudiant);

                System.out.println("Inscription réussie sans UE optionnelle.");
                return; // Fin de la méthode si aucune UE n'est disponible
            }

            // Gérer le cas où des UEs optionnelles sont choisies
            List<UE> ues = new ArrayList<>();
            System.out.println("UEs sélectionnées par l'étudiant :");
            for (UE ue : ueChoisies) {
                ues.add(ue);
            }

            // Création de l'inscription pédagogique avec les UEs sélectionnées
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setFormation(formation);
            inscription.setUes(null);
            inscription.setUesOptionnelles(ues);

            // Enregistrement de l'inscription
            inscriptionDAO.save(inscription);

            // Notifier le responsable de l'inscription
            notificationService.notifierResponsableInscription(responsable.getEmail(), formation.getLibelle(),
                    etudiant);

            System.out.println("Inscription pédagogique réussie avec les UEs sélectionnées !");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'inscription pédagogique : " + e.getMessage());
        }
    }

    public List<Inscription> getInscriptionsByResponsable() {
        Utilisateur utilisateur = SessionManager.getUtilisateur();

        if (utilisateur instanceof ResponsablePedagogique) {
            ResponsablePedagogique responsable = (ResponsablePedagogique) utilisateur;
            return inscriptionDAO.findByResponsable(responsable.getId());
        } else {
            throw new IllegalStateException("L'utilisateur connecté n'est pas un responsable pédagogique.");
        }
    }

    public List<Inscription> getInscriptionsPendingByResponsable() {
        Utilisateur utilisateur = SessionManager.getUtilisateur();

        if (utilisateur instanceof ResponsablePedagogique) {
            ResponsablePedagogique responsable = (ResponsablePedagogique) utilisateur;
            return inscriptionDAO.findPendingByResponsable(responsable.getId());
        } else {
            throw new IllegalStateException("L'utilisateur connecté n'est pas un responsable pédagogique.");
        }
    }

    public List<Inscription> getInscriptionsTreatedByResponsable() {
        Utilisateur utilisateur = SessionManager.getUtilisateur();
        List<Inscription> inscriptions = new ArrayList<>();

        if (utilisateur instanceof ResponsablePedagogique) {
            ResponsablePedagogique responsable = (ResponsablePedagogique) utilisateur;
            for (Inscription inscription : inscriptionDAO.findByResponsable(responsable.getId())) {
                if (inscription.getStatut() != StatutInscription.EN_ATTENTE) {
                    inscriptions.add(inscription);
                }
            }
            return inscriptions;
        } else {
            throw new IllegalStateException("L'utilisateur connecté n'est pas un responsable pédagogique.");
        }
    }

    public Inscription getInscriptionById(Long id) {
        Inscription inscription = inscriptionDAO.findById(id);
        if (inscription == null) {
            throw new IllegalArgumentException("Aucune inscription trouvée avec l'ID : " + id);
        }
        return inscription;
    }

    public void refuserInscription(Long id) {
        Inscription inscription = getInscriptionById(id);

        // 🔹 Récupérer l'étudiant et la formation associée
        Etudiant etudiant = inscription.getEtudiant();
        Formation formation = inscription.getFormation();

        // inscritption refuser
        inscriptionDAO.refuserInscription(id);

        // envoyer une notification
        notificationService.notifierEtudiantInscriptionRefuser(etudiant.getEmail(), formation.getLibelle());
        envoyerEmailRefus(etudiant, inscription.getFormation());

    }

    public void accepterInscription(Long inscriptionId) {
        Inscription inscription = getInscriptionById(inscriptionId);
        if (inscription == null) {
            throw new IllegalArgumentException("L'inscription demandée est introuvable.");
        }

        if (inscription.getStatut() != StatutInscription.EN_ATTENTE) {
            throw new IllegalArgumentException("L'inscription ne peut pas être modifiée.");
        }

        // Inscrire l'étudiant aux UEs obligatoires
        Formation formation = inscription.getFormation();
        List<UE> uesObligatoires = formationService.getRequiredUEs(formation.getId());
        List<UE> uesOptionnelles = inscription.getUesOptionnelles();

        List<UE> ues = new ArrayList<>();
        ues.addAll(uesObligatoires);
        ues.addAll(uesOptionnelles);
        inscription.setUes(ues);

        // inscription.setStatut(StatutInscription.ACCEPTEE);

        inscriptionDAO.update(inscription);

        // accepter l'inscription
        inscriptionDAO.accepterInscription(inscriptionId);

        // Envoyer un email de confirmation
        Etudiant etudiant = inscription.getEtudiant();
        notificationService.notifierEtudiantInscription(etudiant.getEmail(), formation.getLibelle());
        envoyerEmailValidation(etudiant, formation, uesObligatoires, uesOptionnelles);
    }

    private void envoyerEmailValidation(Etudiant etudiant, Formation formation, List<UE> uesObligatoires,
            List<UE> uesOptionnelles) {
        String subject = "Confirmation d'inscription pédagogique";
        StringBuilder body = new StringBuilder();
        body.append("Bonjour ").append(etudiant.getPrenom()).append(",<br><br>")
                .append("Votre inscription à la formation <b>").append(formation.getLibelle())
                .append("</b> a été validée !<br><br>")
                .append("<b>UEs obligatoires :</b> ").append(formatUEs(uesObligatoires)).append("<br>")
                .append("<b>UEs optionnelles :</b> ").append(formatUEs(uesOptionnelles)).append("<br><br>")
                .append("Bonne chance pour votre année académique ! 😊");

        MailUtils.envoyerEmail(etudiant.getEmail(), subject, body.toString());
        System.out.println("Mail d'acceptation envoye avec success");

    }

    private void envoyerEmailRefus(Etudiant etudiant, Formation formation) {
        String subject = "Refus de votre inscription pédagogique";
        String body = "Bonjour " + etudiant.getPrenom() + ",<br><br>"
                + "Nous sommes désolés de vous informer que votre inscription à la formation <b>"
                + formation.getLibelle() + "</b> a été refusée.<br>"
                + "Pour plus d'informations, veuillez contacter votre responsable pédagogique.<br><br>"
                + "Cordialement,<br>L'administration.";

        MailUtils.envoyerEmail(etudiant.getEmail(), subject, body);

        System.out.println("Mail de refus avec success");
    }

    private String formatUEs(List<UE> ues) {
        if (ues.isEmpty()) {
            return "Aucune";
        }
        return ues.stream()
                .map(ue -> ue.getCode() + " - " + ue.getLibelle())
                .collect(Collectors.joining(", "));
    }

    public int countInscriptionsByFormation(Long formationId) {
        int count = 0;
        for (Inscription inscription : getInscriptionsByResponsable()) {
            if (inscription.getFormation().getId() == formationId) {
                count++;
            }
        }
        return count;
    }

}

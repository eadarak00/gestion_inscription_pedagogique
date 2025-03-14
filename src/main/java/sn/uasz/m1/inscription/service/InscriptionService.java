package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.List;

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
            boolean inscriptionExistante = inscriptionDAO.isInscriptionExistante(etudiant.getId(), formationId);
            if (inscriptionExistante) {
                throw new RuntimeException("L'étudiant est déjà inscrit à cette formation.");
            }
                
            // Récupérer le responsable pédagogique de la formation
            ResponsablePedagogique responsable = formationService.getResponsablePedagogique(formationId);
    
            // Récupérer toutes les UEs optionnelles disponibles pour cette formation
            List<UE> uesOptionnellesDisponibles = formationService.getOptionalUEs(formationId);
    
            // Si la formation n'a pas d'UEs optionnelles
            if (uesOptionnellesDisponibles.isEmpty()) {
                System.out.println("Cette formation n'a aucune UE optionnelle. L'inscription se fait sans sélection d'UEs.");
    
                // Créer une inscription sans UEs optionnelles
                Inscription inscription = new Inscription();
                inscription.setEtudiant(etudiant);
                inscription.setFormation(formation);
                inscription.setUesOptionnelles(new ArrayList<>());
                inscriptionDAO.save(inscription);
    
                // Notifier le responsable de l'inscription de l'étudiant
                notificationService.notifierResponsableInscription(responsable.getEmail(), formation.getLibelle(), etudiant);
    
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
            inscription.setUesOptionnelles(ues);
    
            // Enregistrement de l'inscription
            inscriptionDAO.save(inscription);
    
            // Notifier le responsable de l'inscription
            notificationService.notifierResponsableInscription(responsable.getEmail(), formation.getLibelle(), etudiant);
    
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
    
}

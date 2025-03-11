package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.List;

import sn.uasz.m1.inscription.dao.InscriptionDAO;
import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Inscription;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

public class InscriptionService {
    private final InscriptionDAO inscriptionDAO;
    private final UEDAO ueDAO;
    private final EtudiantService etudiantService;
    private final FormationService formationService;

    public InscriptionService() {
        this.inscriptionDAO = new InscriptionDAO();
        this.ueDAO = new UEDAO();
        this.etudiantService = new EtudiantService();
        this.formationService = new FormationService();

    }

    /**
     * Permet à un étudiant de s'inscrire pédagogiquement en choisissant ses UEs
     * optionnelles.
     */
    // public void inscrireEtudiant(Long formationId, List<Long> ueIdsChoisies) {
    // try {
    // // Récupérer l'utilisateur connecté
    // Utilisateur connectedUser = SessionManager.getUtilisateur();
    // Etudiant etudiant = etudiantService.getEtudiantById(connectedUser.getId());
    // if (etudiant == null) {
    // throw new RuntimeException("Étudiant non trouvé.");
    // }

    // // Vérifier que la formation existe
    // Formation formation = formationService.getFormationById(formationId);
    // if (formation == null) {
    // throw new IllegalArgumentException("Formation non trouvée.");
    // }

    // // Récupérer toutes les UEs optionnelles disponibles pour cette formation
    // List<UE> uesOptionnellesDisponibles =
    // formationService.getOptionalUEs(formationId);

    // // Si la formation n'a pas d'UEs optionnelles
    // if (uesOptionnellesDisponibles.isEmpty()) {
    // // Permettre l'inscription sans UEs optionnelles
    // System.out.println("Cette formation n'a aucune UE optionnelle. L'inscription
    // se fait sans sélection d'UEs.");
    // // Créer une inscription sans UEs optionnelles
    // Inscription inscription = new Inscription();
    // inscription.setEtudiant(etudiant);
    // inscription.setFormation(formation);
    // inscription.setUesOptionnelles(new ArrayList<>());
    // inscriptionDAO.save(inscription);
    // System.out.println("Inscription pédagogique réussie sans UE optionnelle !");
    // return;
    // }

    // // Vérifier que toutes les UEs choisies sont réellement optionnelles
    // // List<UE> uesChoisies = new ArrayList<>();
    // // for (Long ueId : ueIdsChoisies) {
    // // UE ue = ueDAO.findById(ueId);
    // // if (ue == null || ue.isObligatoire() ||
    // !uesOptionnellesDisponibles.contains(ue)) {
    // // throw new IllegalArgumentException("L'UE choisie (ID: " + ueId + ") n'est
    // pas valide.");
    // // }
    // // uesChoisies.add(ue);
    // // }

    // List<UE> uesChoisies = new ArrayList<>();

    // // 🔹 Logguer les UEs sélectionnées avant vérification
    // System.out.println("📌 UEs sélectionnées par l'étudiant :");
    // for (Long ueId : ueIdsChoisies) {
    // UE ue = ueDAO.findById(ueId);
    // if (ue != null) {
    // String statut = ue.isObligatoire() ? "Obligatoire" : "Optionnelle";
    // System.out.println("✔ UE trouvée → ID: " + ue.getId() + ", Code: " +
    // ue.getCode() + ", Libellé: " + ue.getLibelle() + " (" + statut + ")");
    // } else {
    // System.out.println("❌ UE introuvable → ID: " + ueId);
    // }
    // }

    // // 🔹 Vérification des UEs
    // for (Long ueId : ueIdsChoisies) {
    // UE ue = ueDAO.findById(ueId);
    // if (ue == null || ue.isObligatoire() ||
    // !uesOptionnellesDisponibles.contains(ue)) {
    // String raison = (ue == null) ? "introuvable" : (ue.isObligatoire() ?
    // "obligatoire" : "optionnelle");
    // throw new IllegalArgumentException("⚠ L'UE choisie (ID: " + ueId + ") est
    // invalide (" + raison + ").");
    // }
    // uesChoisies.add(ue);
    // }

    // // Création de l'inscription pédagogique
    // Inscription inscription = new Inscription();
    // inscription.setEtudiant(etudiant);
    // inscription.setFormation(formation);
    // inscription.setUesOptionnelles(uesChoisies);

    // // Enregistrement de l'inscription
    // inscriptionDAO.save(inscription);

    // System.out.println(" Inscription pédagogique réussie !");
    // } catch (Exception e) {
    // throw new RuntimeException("Erreur lors de l'inscription pédagogique : " +
    // e.getMessage());
    // }
    // }

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

            // Récupérer toutes les UEs optionnelles disponibles pour cette formation
            List<UE> uesOptionnellesDisponibles = formationService.getOptionalUEs(formationId);

            // Si la formation n'a pas d'UEs optionnelles
            if (uesOptionnellesDisponibles.isEmpty()) {
                // Permettre l'inscription sans UEs optionnelles
                System.out.println(
                        "Cette formation n'a aucune UE optionnelle. L'inscription se fait sans sélection d'UEs.");
                // Créer une inscription sans UEs optionnelles
                Inscription inscription = new Inscription();
                inscription.setEtudiant(etudiant);
                inscription.setFormation(formation);
                inscription.setUesOptionnelles(new ArrayList<>());
                inscriptionDAO.save(inscription);
                System.out.println("Inscription pédagogique réussie sans UE optionnelle !");
                return;
            }

            System.out.println("📌 Liste des UEs optionnelles disponibles pour la formation " + formation.getLibelle()
                    + " (ID: " + formationId + "):");
            for (UE ue : uesOptionnellesDisponibles) {
                System.out.println("✔ UE Disponible → ID: " + ue.getId() + ", Code: " + ue.getCode() + ", Libellé: "
                        + ue.getLibelle());
            }
            List<UE> ues = new ArrayList<>();

            // 🔹 Logguer les UEs sélectionnées avant vérification
            System.out.println("UEs sélectionnées par l'étudiant :");
            for (UE ue : ueChoisies) {
                String statut = ue.isObligatoire() ? "Obligatoire" : "Optionnelle";
                System.out.println("✔ UE trouvée → ID: " + ue.getId() + ", Code: " +
                        ue.getCode() + ", Libellé: " + ue.getLibelle() + " (" + statut + ")");

                ues.add(ue);
            }

            // Création de l'inscription pédagogique
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setFormation(formation);
            inscription.setUesOptionnelles(ues);

            // Enregistrement de l'inscription
            inscriptionDAO.save(inscription);

            System.out.println(" Inscription pédagogique réussie !");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'inscription pédagogique : " + e.getMessage());
        }
    }

}

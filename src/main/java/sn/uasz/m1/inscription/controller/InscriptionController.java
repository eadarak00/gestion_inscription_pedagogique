package sn.uasz.m1.inscription.controller;

import java.util.List;

import sn.uasz.m1.inscription.model.Inscription;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.service.InscriptionService;

public class InscriptionController {
    private final InscriptionService inscriptionService;

    public InscriptionController(){
        this.inscriptionService = new InscriptionService();
    }

    public String inscrireEtudiant(Long formationId, List<UE> uesChoisies) {
        try {
            inscriptionService.inscrireEtudiant(formationId, uesChoisies);
            return "Inscription réussie ! En attente de validation.";
        } catch (IllegalArgumentException e) {
            return "Erreur : " + e.getMessage();
        } catch (RuntimeException e) {
            return "Échec de l'inscription : " + e.getMessage();
        }
    }

    public List<Inscription> listeInscriptionsResponsable(){
        return inscriptionService.getInscriptionsByResponsable();
    }

    public String refuserInscription(Long inscriptionId) {
        try {
            inscriptionService.refuserInscription(inscriptionId);
            return "Inscription refusée avec succès.";
        } catch (IllegalArgumentException e) {
            return "Erreur : " + e.getMessage();
        } catch (RuntimeException e) {
            return "Une erreur est survenue lors du refus de l'inscription.";
        }
    }

    public String accepterInscription(Long inscriptionId) {
        try {
            inscriptionService.accepterInscription(inscriptionId);
            return "Inscription acceptée avec succès.";
        } catch (IllegalArgumentException e) {
            return "Erreur : " + e.getMessage();
        } catch (RuntimeException e) {
            return "Une erreur est survenue lors de l'acceptation de l'inscription.";
        }
    }
}

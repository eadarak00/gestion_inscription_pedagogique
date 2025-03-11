package sn.uasz.m1.inscription.controller;

import java.util.List;

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
}

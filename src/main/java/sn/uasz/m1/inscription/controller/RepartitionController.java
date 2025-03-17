package sn.uasz.m1.inscription.controller;

import sn.uasz.m1.inscription.service.RepartitionService;

public class RepartitionController {
    private final RepartitionService repartitionService;

    public RepartitionController() {
        this.repartitionService = new RepartitionService();
    }

    public String repartitionAutomatique(Long formationId) {
        try {
            repartitionService.repartirEtudiants(formationId);
            System.out.println("Répartition mise à jour !");
            return "Répartition des étudiants réussie.";
        } catch (Exception e) {
            System.out.println("Erreur lors de la répartition : " + e.getMessage());
            return "La répartition des étudiants a échoué. Veuillez réessayer.";
        }
    }
    
}

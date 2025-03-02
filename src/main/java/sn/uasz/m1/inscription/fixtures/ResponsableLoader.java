package sn.uasz.m1.inscription.fixtures;

import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;

public class ResponsableLoader {
    private ResponsablePedagogiqueService service;

    public ResponsableLoader() {
        this.service = new ResponsablePedagogiqueService();
    }

    public void chargerResponsableParDefaut() {
        // Création d'un responsable pédagogique
        ResponsablePedagogique responsable = new ResponsablePedagogique();
        responsable.setPrenom("Serigne");
        responsable.setNom("Diagne");
        responsable.setEmail("sdiagne@univ-zig.sn");
        responsable.setMotDePasse("passer123");
        responsable.setDepartement("Informatique");

        try {
            // Appeler la méthode pour créer un responsable
            ResponsablePedagogique savedResponsable = service.createResponsable(responsable);
            System.out.println("Responsable créé avec succès ! ID : " + savedResponsable.getId());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // Création du deuxième responsable pédagogique
        ResponsablePedagogique responsable2 = new ResponsablePedagogique();
        responsable2.setPrenom("Mor"); // <- Ici, c'était responsable au lieu de responsable2
        responsable2.setNom("NDONGO");
        responsable2.setEmail("mn@univ-zig.sn");
        responsable2.setMotDePasse("passer123");
        responsable2.setDepartement("Economie");

        try {
            // Appeler la méthode pour créer un responsable
            ResponsablePedagogique savedResponsable = service.createResponsable(responsable2);
            System.out.println("Responsable créé avec succès ! ID : " + savedResponsable.getId());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}

package sn.uasz.m1.inscription;

import sn.uasz.m1.inscription.dao.UtilisateurDAO;
import sn.uasz.m1.inscription.fixtures.RoleLoader;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
import sn.uasz.m1.inscription.utils.SecurityUtil;

public class Main {
    public static void main(String[] args) {
        // RoleLoader roleLoader = new RoleLoader();
        // roleLoader.chargerRolesParDefaut();

        // ResponsablePedagogiqueService service = new ResponsablePedagogiqueService();
        
        // // Création d'un responsable pédagogique
        // ResponsablePedagogique responsable = new ResponsablePedagogique();
        // responsable.setPrenom("Serigne");
        // responsable.setNom("Diagne");
        // responsable.setEmail("sdiagne@univ-zig.sn");
        // responsable.setMotDePasse("passer123");
        // responsable.setDepartement("Informatique");

        // try {
        //     // Appeler la méthode pour créer un responsable
        //     ResponsablePedagogique savedResponsable = service.createResponsable(responsable);
        //     System.out.println("Responsable créé avec succès ! ID : " + savedResponsable.getId());
        // } catch (Exception e) {
        //     System.out.println("Erreur : " + e.getMessage());
        // }

        AuthentificationService authService = new AuthentificationService();
        
        // Simuler un utilisateur avec un mot de passe chiffré
        String email = "sdiagne@univ-zig.sn";
        String password = "passer123";
        String hashedPassword = SecurityUtil.hasherMotDePasse(password);
        System.out.println("Mot de passe chiffré : " + hashedPassword);

        // Tester l'authentification
        boolean connexion = authService.authentifier(email, password);
        System.out.println("Authentification : " + (connexion ? "Réussie" : "Échec"));

        // Déconnexion
        authService.deconnecter();
    }


}   
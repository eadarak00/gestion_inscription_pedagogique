package sn.uasz.m1.inscription;

import java.time.LocalDate;

import sn.uasz.m1.inscription.dao.UtilisateurDAO;
import sn.uasz.m1.inscription.fixtures.RoleLoader;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.enumeration.Sexe;
import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.service.EtudiantService;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
import sn.uasz.m1.inscription.utils.SecurityUtil;
import sn.uasz.m1.inscription.view.HomeUI;

public class Main {
    // public static void main(String[] args) {
    //     // RoleLoader roleLoader = new RoleLoader();
    //     // roleLoader.chargerRolesParDefaut();

    //     ResponsablePedagogiqueService service = new ResponsablePedagogiqueService();
        
    //     // Création d'un responsable pédagogique
    //     // ResponsablePedagogique responsable = new ResponsablePedagogique();
    //     // responsable.setPrenom("Serigne");
    //     // responsable.setNom("Diagne");
    //     // responsable.setEmail("sdiagne@univ-zig.sn");
    //     // responsable.setMotDePasse("passer123");
    //     // responsable.setDepartement("Informatique");

    //     // try {
    //     //     // Appeler la méthode pour créer un responsable
    //     //     ResponsablePedagogique savedResponsable = service.createResponsable(responsable);
    //     //     System.out.println("Responsable créé avec succès ! ID : " + savedResponsable.getId());
    //     // } catch (Exception e) {
    //     //     System.out.println("Erreur : " + e.getMessage());
    //     // }

    //     // EtudiantService etudiantService = new EtudiantService();
    //     // Etudiant etudiant = new Etudiant();
    //     // etudiant.setPrenom("Aliou");
    //     // etudiant.setNom("Diop");
    //     // etudiant.setEmail("adiop@zig.univ.sn");
    //     // etudiant.setMotDePasse("passer123");
    //     // etudiant.setIne("202000001");
    //     // etudiant.setDateNaissance(LocalDate.of(2000, 5, 10));
    //     // etudiant.setSexe(Sexe.HOMME);
    //     // etudiant.setAdresse("Ziguinchor");
    //     // try {
    //     //     // appel de la methode permettant de sauvegarder un etudiant
    //     //     Etudiant savedEtudiant = etudiantService.createEtudiant(etudiant);
    //     //     System.out.println("Responsable créé avec succès ! ID : " + savedEtudiant.getId());
    //     // } catch (Exception e) {
    //     //     System.out.println("Erreur : " + e.getMessage());
    //     // }
        
    
    //     // AuthentificationService authService = new AuthentificationService();
        
    //     // // Simuler un utilisateur avec un mot de passe chiffré
    //     // String email = "sdiagne@univ-zig.sn";
    //     // String password = "passer123";
    //     // String hashedPassword = SecurityUtil.hasherMotDePasse(password);
    //     // System.out.println("Mot de passe chiffré : " + hashedPassword);

    //     // // Tester l'authentification
    //     // boolean connexion = authService.authentifier(email, password);
    //     // System.out.println("Authentification : " + (connexion ? "Réussie" : "Échec"));

    //     // // Déconnexion
    //     // authService.deconnecter();
    // }


    public static void main(String[] args) {
        HomeUI home = new HomeUI();
        home.afficher();
    }

}   
package sn.uasz.m1.inscription.controller;

import java.time.LocalDate;

import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.enumeration.Sexe;
import sn.uasz.m1.inscription.service.EtudiantService;

public class EtudiantController {
    
    private EtudiantService etudiantService;
    
    public EtudiantController(){
        etudiantService = new EtudiantService();
    }

    public String inscrire(String ine, String nom, String prenom, String email, String motDePasse, LocalDate dateNaissance, Sexe sexe, String adresse) {
        // Vérification des champs obligatoires
        if (ine.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || dateNaissance == null || sexe == null || adresse.isEmpty()) {
            return "Tous les champs sont obligatoires.";
        }

        // Vérification du format de l'email
        if (!email.endsWith("@zig.univ.sn")) {
            return "L'email doit être sous le domaine @zig.univ.sn";
        }

        // // Vérification de la longueur du mot de passe
        // if (motDePasse.length() < 6) {
        //     return "⚠️Le mot de passe doit contenir au moins 6 caractères.";
        // }

        // Création de l'objet étudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setIne(ine);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        etudiant.setMotDePasse(motDePasse);
        etudiant.setAdresse(adresse);
        etudiant.setSexe(sexe);

        // Sauvegarde en base de données
        etudiantService.createEtudiant(etudiant);
        return "Étudiant inscrit avec succès.";
    }
}

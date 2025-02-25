package sn.uasz.m1.inscription.utils;

import sn.uasz.m1.inscription.model.Utilisateur;

public class SessionManager {
    private static Utilisateur utilisateurConnecte;

    public static void setUtilisateur(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static void logout(){
        utilisateurConnecte = null;
    }
}

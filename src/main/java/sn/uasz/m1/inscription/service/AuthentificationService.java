package sn.uasz.m1.inscription.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.m1.inscription.dao.UtilisateurDAO;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SecurityUtil;
import sn.uasz.m1.inscription.utils.SessionManager;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationService {
      private UtilisateurDAO utilisateurDAO ;
   
    public boolean authentifier(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.trouverParEmail(email);
        if (utilisateur == null) {
            log.info("Utilisateur introuvable !");
            return false;
        }

        if (SecurityUtil.verifierMotdePasse(motDePasse, utilisateur.getMotDePasse())) {
            SessionManager.setUtilisateur(utilisateur);
            log.info("Connexion réussie : " + utilisateur.getEmail());
            return true;
        }

        System.out.println("Mot de passe incorrect !");
        return false;
    }

    /**
     * Déconnecte l'utilisateur en supprimant la session.
     */
    public void deconnecter() {
        SessionManager.logout();
        System.out.println("Déconnexion réussie.");
    }
}

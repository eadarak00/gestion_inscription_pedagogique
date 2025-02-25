
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import sn.uasz.m1.inscription.dao.UtilisateurDAO;
// import sn.uasz.m1.inscription.model.Utilisateur;
// import sn.uasz.m1.inscription.service.AuthentificationService;
// import sn.uasz.m1.inscription.utils.SecurityUtil;
// import sn.uasz.m1.inscription.utils.SessionManager;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class AuthentificationServiceTest {

//     @Mock
//     private UtilisateurDAO utilisateurDAO;

//     @InjectMocks
//     private AuthentificationService authentificationService;

//     @BeforeEach
//     void setUp() {
//         authentificationService = new AuthentificationService(utilisateurDAO);
//     }

//     @Test
//     void testAuthentifier_UtilisateurInexistant() {
//         // Simuler un utilisateur introuvable
//         when(utilisateurDAO.trouverParEmail("inconnu@univ-zig.sn")).thenReturn(null);

//         // Exécuter la méthode
//         boolean resultat = authentificationService.authentifier("inconnu@univ-zig.sn", "password");

//         // Vérifier que l'authentification échoue
//         assertFalse(resultat);
//         verify(utilisateurDAO, times(1)).trouverParEmail("inconnu@univ-zig.sn");
//     }

//     @Test
//     void testAuthentifier_MotDePasseIncorrect() {
//         Utilisateur utilisateur = new Utilisateur();
//         utilisateur.setEmail("user@univ-zig.sn");
//         utilisateur.setMotDePasse("hashed_password");

//         // Simuler un utilisateur trouvé avec un mauvais mot de passe
//         when(utilisateurDAO.trouverParEmail("user@univ-zig.sn")).thenReturn(utilisateur);
//         mockStatic(SecurityUtil.class);
//         when(SecurityUtil.verifierMotdePasse("wrong_password", "hashed_password")).thenReturn(false);

//         boolean resultat = authentificationService.authentifier("user@univ-zig.sn", "wrong_password");

//         assertFalse(resultat);
//         verify(utilisateurDAO, times(1)).trouverParEmail("user@univ-zig.sn");
//     }

//     @Test
//     void testAuthentifier_Succes() {
//         Utilisateur utilisateur = new Utilisateur();
//         utilisateur.setEmail("user@univ-zig.sn");
//         utilisateur.setMotDePasse("hashed_password");

//         // Simuler un utilisateur trouvé et une authentification réussie
//         when(utilisateurDAO.trouverParEmail("user@univ-zig.sn")).thenReturn(utilisateur);
//         mockStatic(SecurityUtil.class);
//         when(SecurityUtil.verifierMotdePasse("correct_password", "hashed_password")).thenReturn(true);

//         boolean resultat = authentificationService.authentifier("user@univ-zig.sn", "correct_password");

//         assertTrue(resultat);
//         verify(utilisateurDAO, times(1)).trouverParEmail("user@univ-zig.sn");

//         // Vérifier que la session est bien mise à jour
//         mockStatic(SessionManager.class);
//         verify(SessionManager.class, times(1));
//         SessionManager.setUtilisateur(utilisateur);
//     }

//     @Test
//     void testDeconnecter() {
//         authentificationService.deconnecter();

//         // Vérifier que SessionManager.logout() a été appelé
//         mockStatic(SessionManager.class);
//         verify(SessionManager.class, times(1));
//         SessionManager.logout();
//     }
// }

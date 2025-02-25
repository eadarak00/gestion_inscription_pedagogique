// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import sn.uasz.m1.inscription.dao.RoleDAO;
// import sn.uasz.m1.inscription.model.Role;
// import sn.uasz.m1.inscription.service.RoleService;

// import static org.mockito.Mockito.*;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(MockitoExtension.class)  // Initialisation des mocks
// class RoleServiceTest {

//     @Mock
//     private RoleDAO roleDAO;

//     @InjectMocks
//     private RoleService roleService;

//     private Role role;

//     @BeforeEach
//     void setUp() {
//         role = new Role();
//         role.setId(1L);
//         role.setLibelle("ETUDIANT");
//     }

//     @Test
//     void testAjouterRole() {
//         // Simuler que le rôle n'existe pas déjà dans la base de données
//         when(roleDAO.trouverParLibelle("ADMINISTRATEUR")).thenReturn(null);

//         // Exécuter la méthode
//         boolean result = roleService.ajouterRole("ADMINISTRATEUR");

//         // Vérifier le résultat
//         assertTrue(result);
//         verify(roleDAO, times(1)).ajouter(Mockito.any(Role.class));
//     }

//     @Test
//     void testAjouterRoleExistants() {
//         // Simuler que le rôle existe déjà dans la base de données
//         when(roleDAO.trouverParLibelle("ETUDIANT")).thenReturn(role);

//         // Exécuter la méthode
//         boolean result = roleService.ajouterRole("ETUDIANT");

//         // Vérifier le résultat
//         assertFalse(result);
//         verify(roleDAO, never()).ajouter(Mockito.any(Role.class));
//     }

//     @Test
//     void testTrouverRoleParId() {
//         // Simuler le comportement de RoleDAO
//         when(roleDAO.trouverParId(1L)).thenReturn(role);

//         // Exécuter la méthode
//         Role result = roleService.trouverParId(1L);

//         // Vérifier le résultat
//         assertNotNull(result);
//         assertEquals("ETUDIANT", result.getLibelle());
//         verify(roleDAO, times(1)).trouverParId(1L);
//     }

//     @Test
//     void testTrouverRoleParLibelle() {
//         // Simuler le comportement de RoleDAO
//         when(roleDAO.trouverParLibelle("ETUDIANT")).thenReturn(role);

//         // Exécuter la méthode
//         Role result = roleService.trouverParLibelle("ETUDIANT");

//         // Vérifier le résultat
//         assertNotNull(result);
//         assertEquals("ETUDIANT", result.getLibelle());
//         verify(roleDAO, times(1)).trouverParLibelle("ETUDIANT");
//     }

//     @Test
//     void testListerRoles() {
//         // Simuler le comportement de RoleDAO
//         when(roleDAO.listerTous()).thenReturn(List.of(role));

//         // Exécuter la méthode
//         var result = roleService.listerRoles();

//         // Vérifier le résultat
//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertEquals("ETUDIANT", result.get(0).getLibelle());
//         verify(roleDAO, times(1)).listerTous();
//     }

//     @Test
//     void testSupprimerRole() {
//         // Simuler le comportement de RoleDAO pour trouver un rôle
//         when(roleDAO.trouverParId(1L)).thenReturn(role);

//         // Exécuter la méthode
//         boolean result = roleService.supprimerRole(1L);

//         // Vérifier le résultat
//         assertTrue(result);
//         verify(roleDAO, times(1)).supprimer(1L);
//     }

//     @Test
//     void testSupprimerRoleNonExistant() {
//         // Simuler que le rôle n'existe pas
//         when(roleDAO.trouverParId(2L)).thenReturn(null);

//         // Exécuter la méthode
//         boolean result = roleService.supprimerRole(2L);

//         // Vérifier le résultat
//         assertFalse(result);
//         verify(roleDAO, never()).supprimer(2L);
//     }
// }

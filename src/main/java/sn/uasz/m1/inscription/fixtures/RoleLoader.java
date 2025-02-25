package sn.uasz.m1.inscription.fixtures;

import sn.uasz.m1.inscription.service.RoleService;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoleLoader {
    private RoleService roleService = new RoleService();

    public void chargerRolesParDefaut() {
        // Liste des rôles à insérer par défaut
        List<String> rolesParDefaut = List.of("SUPER_ADMIN", "RESPONSABLE", "ETUDIANT");

        for (String libelleRole : rolesParDefaut) {
            roleService.ajouterRole(libelleRole);
        }
    }
}

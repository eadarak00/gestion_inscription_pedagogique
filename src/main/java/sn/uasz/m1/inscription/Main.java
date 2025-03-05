package sn.uasz.m1.inscription;

import sn.uasz.m1.inscription.fixtures.EnseignantLoader;
import sn.uasz.m1.inscription.fixtures.ResponsableLoader;
import sn.uasz.m1.inscription.fixtures.RoleLoader;
import sn.uasz.m1.inscription.view.HomeUI;

public class Main {

    public static void main(String[] args) {
        // RoleLoader roleLoader = new RoleLoader();
        // roleLoader.chargerRolesParDefaut();
        // ResponsableLoader responsableLoader = new ResponsableLoader();
        // responsableLoader.chargerResponsableParDefaut();
        // EnseignantLoader enseignantLoader = new EnseignantLoader();
        // enseignantLoader.loadEnseignants();
        HomeUI home = new HomeUI();
        home.afficher();
    }

}
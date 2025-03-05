package sn.uasz.m1.inscription.fixtures;

import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.service.EnseignantService;

public class EnseignantLoader {

    public void loadEnseignants() {
        EnseignantService enseignantService = new EnseignantService();

        // Création des enseignants avec leur spécialité
        Enseignant enseignant1 = new Enseignant("DIALLO", "Mamadou", "mamadou.diallo@univ-zig.sn", "Mathématiques");
        Enseignant enseignant2 = new Enseignant("BA", "Aissatou", "aissatou.ba@univ-zig.sn", "Informatique");
        Enseignant enseignant3 = new Enseignant("NDIAYE", "Ibrahima", "ibrahima.ndiaye@univ-zig.sn", "Physique");
        Enseignant enseignant4 = new Enseignant("SOW", "Fatou", "fatou.sow@univ-zig.sn", "Chimie");
        Enseignant enseignant5 = new Enseignant("DIOP", "Cheikh", "cheikh.diop@univ-zig.sn", "Biologie");

        // Ajout des enseignants à la base de données
        enseignantService.createEnseignant(enseignant1);
        enseignantService.createEnseignant(enseignant2);
        enseignantService.createEnseignant(enseignant3);
        enseignantService.createEnseignant(enseignant4);
        enseignantService.createEnseignant(enseignant5);

        System.out.println("✅ 5 enseignants ajoutés avec succès !");
    }
}

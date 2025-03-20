package sn.uasz.m1.inscription.fixtures;

import java.util.*;

import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.service.EnseignantService;
import sn.uasz.m1.inscription.service.FormationService;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
import sn.uasz.m1.inscription.service.UEService;

public class UELoader {
    private final UEService ueService = new UEService();
    private final UEDAO uedao = new UEDAO();
    private final EnseignantService enseignantService = new EnseignantService();
    private final ResponsablePedagogiqueService rService = new ResponsablePedagogiqueService();
    private FormationService formationService = new FormationService();

    Random random = new Random();

    public void loadUEs() {
    List<UE> ues = new ArrayList<>();
    Formation formation = formationService.getFormationById(Long.parseLong("1"));

    // Tableau des UEs extraites du fichier PDF
    String[][] ueData = {
        {"IN4111", "Génie logiciel", "Fondamentale"},
        {"IN4112", "Bases de données avancées", "Fondamentale"},
        {"IN4121", "Programmation objet", "Fondamentale"},
        {"IN4122", "Programmation avancée", "Fondamentale"},
        {"IN4131", "Architecture des réseaux LAN/WAN", "Fondamentale"},
        {"IN4132", "Routage IP", "Fondamentale"},
        {"IN4133", "Services réseaux", "Fondamentale"},
        {"IN4141", "Probabilités et statistiques", "Fondamentale"},
        {"IN4142", "Algorithmique des graphes", "Fondamentale"},
        {"IN4151", "Technique de communications", "Transversale"},
        {"IN4152", "Anglais", "Transversale"},
        {"GL4211", "Administration Bases de Données", "Fondamentale"},
        {"GL4212", "Administration Systèmes", "Fondamentale"},
        {"GL4213", "Administration Réseaux", "Fondamentale"},
        {"IN422", "Technologies XML", "Fondamentale"},
        {"GL4231", "Projet", "Fondamentale"},
        {"GL4232", "Stage", "Fondamentale"},
        {"GL4241", "Web services", "Transversale"},
        {"GL4242", "Technologies du Web", "Transversale"},
        {"GL4251", "Programmation fonctionnelle", "Fondamentale"},
        {"GL4252", "Intelligence artificielle", "Fondamentale"},
        {"GL4261", "Développement mobile", "Fondamentale"},
        {"GL4262", "Programmation réseaux", "Transversale"},
        {"GL4263", "Programmation parallèle", "Transversale"}
    };

    for (String[] data : ueData) {
        UE ue = new UE();
        ue.setCode(data[0]);
        ue.setLibelle(data[1]);
        // Définir si c'est une UE obligatoire ou optionnelle (exemple arbitraire)
        ue.setObligatoire(data[2].equals("Fondamentale"));

        // Attribution aléatoire des crédits entre 1 et 8
        int credits = 1 + random.nextInt(8);
        ue.setCredit(credits);

        // Attribution aléatoire des crédits entre 1 et 8
        int coefficient = 1 + random.nextInt(4);
        ue.setCoefficient(coefficient);

        // Attribution aléatoire des crédits entre 40 et 8
        int vh = 40 + random.nextInt(120);
        ue.setVolumeHoraire(vh);

        Long enseignant = 1 + random.nextLong(5);
        ue.setEnseignant(enseignantService.getEnseignantById(enseignant));

        Long responsable = Long.parseLong("1");
        ue.setResponsable(rService.getResponsableById(responsable));

        ue.setFormation(formation);
        ues.add(ue);
    }

    for (UE ue : ues) {
        uedao.save(ue);
        System.out.println("UE ajoutée : " + ue.getCode() + " - " + ue.getLibelle());
    }
}


}

package sn.uasz.m1.inscription.fixtures;

import java.util.*;

import sn.uasz.m1.inscription.dao.FormationDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.service.FormationService;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;

public class FormationLoader {
    private List<Formation> formations = new ArrayList<>();
    private ResponsablePedagogiqueService rService = new ResponsablePedagogiqueService();
    private FormationDAO formationService = new FormationDAO();

    public void loadFormation() {
        Long responsableId = Long.parseLong("1");
        ResponsablePedagogique responsable = rService.getResponsableById(responsableId);

        // Tableau des formaion
        String[][] formationData = {
                { "Master 1 Informatique", "1" },
                { "Master 2 Informatique", "2" },
                { "Licence 1 Ingenierie Informatique", "1" },
                { "Licence 2 Ingenierie Informatique", "2" },
                { "Licence 3 Ingenierie Informatique", "3" },
                { "Licence 3 Informatique", "3" },
        };

        for (String[] data : formationData) {
            Formation formation = new Formation();
            formation.setLibelle(data[0]);
            formation.setNiveau(Integer.parseInt(data[1]));
            formation.setResponsable(responsable);

            formations.add(formation);
        }

        for (Formation formation : formations) {
            formationService.save(formation);
        }
        System.out.println("Formations loaded into the database.");
    }
}

package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.List;

import sn.uasz.m1.inscription.dao.FormationDAO;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

public class FormationService {
    private final FormationDAO formationDAO;
    private final ResponsablePedagogiqueService rService;

    public FormationService(){
        formationDAO = new FormationDAO();
        rService = new ResponsablePedagogiqueService();
    }

    public Formation createFormation(Formation formation){
        if (formation == null) {
            throw new IllegalArgumentException("la formation ne peut pas être null !");
        }
        
        Utilisateur user = SessionManager.getUtilisateur();
        ResponsablePedagogique  responsable =  rService.getResponsableById(user.getId());
        formation.setResponsable(responsable);
        return formationDAO.save(formation);
    }

    public Formation getFormationById(Long id){
        Formation formation = formationDAO.findById(id);
        if (formation == null) {
            throw new IllegalArgumentException("Aucune formation  trouvée avec l'ID : " + id);
        }
        return formation;
    } 

    public List<Formation> getAllFormations(){
        return formationDAO.findAll();
    }

    public List<Formation> getFormationsByResponsable(){
        Utilisateur user = SessionManager.getUtilisateur();
        ResponsablePedagogique responsable = rService.getResponsableById(user.getId());
        return formationDAO.findByResponsable(responsable);
    }

    public Formation updateFormation(Long id, Formation updatedFormation){
        if(updatedFormation == null){
            throw new IllegalArgumentException("Les nouvelles données de la formation ne peuvent pas être null");
        }

        return formationDAO.update(id, updatedFormation);
    }

    public void deleteFormationById(Long id){
        formationDAO.delete(id);
    }

    public Formation getFormationByLibelle(String libelle){
        Formation formation = formationDAO.findByLibelle(libelle);
        if (formation == null) {
            throw new IllegalArgumentException("Aucune formation  trouvée avec le libelle : " + libelle);
        }
        return formation;
    }

}

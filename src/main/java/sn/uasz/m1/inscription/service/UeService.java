package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.UeDAO;
import sn.uasz.m1.inscription.model.Ue;

public class UeService {
    private UeDAO ueDAO;

    //Ajouter un Ue
    public void ajouterUe(Ue ue){
        ueDAO.save(ue);
    }

    //Supprimer un Ue
    public void supprimerUe(Long id){
        Ue ueExisting=ueDAO.findById(id);
        if (ueExisting==null) {
            throw new IllegalArgumentException("L'ue n'existe pas");
        }
        ueDAO.delete(id);
    }

    //Modifier un Ue
    public Ue modifierUe(Long id,Ue ue){
        Ue ueExisting=ueDAO.findById(id);

        if(ueExisting==null){
            throw new IllegalArgumentException("L'ue n'existe pas");
        }
        return ueDAO.update(id, ue);
    }

    //Afficher la liste des ues
    public List<Ue> afficherLesUes(){
        return ueDAO.findAll();
    }

    //Verifier si un Ue existe
    public Ue trouverUe(Long id){
        return ueDAO.findById(id);
    }

    //Liste des ues gerer par un enseignant
    public List<Ue> listeUeEnseignant(Long id){
        return ueDAO.trouverParEnseignant(id);
    }

}

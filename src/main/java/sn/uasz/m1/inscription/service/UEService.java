package sn.uasz.m1.inscription.service;

import java.util.List;

import sn.uasz.m1.inscription.dao.UEDAO;
import sn.uasz.m1.inscription.model.UE;

public class UEService {
    // private UEDAO ueDAO;

    // //Ajouter un Ue
    // public void ajouterUe(UE ue){
    //     ueDAO.save(ue);
    // }

    // //Supprimer un Ue
    // public void supprimerUe(Long id){
    //     UE ueExisting=ueDAO.findById(id);
    //     if (ueExisting==null) {
    //         throw new IllegalArgumentException("L'ue n'existe pas");
    //     }
    //     ueDAO.delete(id);
    // }

    // //Modifier un Ue
    // public UE modifierUe(Long id,UE ue){
    //     UE ueExisting=ueDAO.findById(id);

    //     if(ueExisting==null){
    //         throw new IllegalArgumentException("L'ue n'existe pas");
    //     }
    //     return ueDAO.update(id, ue);
    // }

    // //Afficher la liste des ues
    // public List<UE> afficherLesUes(){
    //     return ueDAO.findAll();
    // }

    // //Verifier si un Ue existe
    // public UE trouverUe(Long id){
    //     return ueDAO.findById(id);
    // }

    // //Liste des ues gerer par un enseignant
    // public List<UE> listeUeEnseignant(Long id){
    //     return ueDAO.trouverParEnseignant(id);
    // }

}

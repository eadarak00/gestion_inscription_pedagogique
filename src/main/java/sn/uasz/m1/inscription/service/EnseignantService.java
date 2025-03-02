package sn.uasz.m1.inscription.service;
import java.util.ArrayList;
import java.util.List;

import sn.uasz.m1.inscription.dao.EnseignantDAO;
import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.model.UE;


public class EnseignantService {

    // private EnseignantDAO enseignantDAO;

    // //Ajouter un Enseignant
    // public void ajouterEnseignant(Enseignant enseignant) {
    //     enseignantDAO.save(enseignant);
    // }

    // //Modifier un Enseignant
    // public Enseignant modifierEnseignant(Long id,Enseignant enseignant){
    //     Enseignant enseignant2= enseignantDAO.findById(id);
    //     if (enseignant2==null){    
    //         throw new IllegalArgumentException("L'enseignant n'existe pas");
    //     }
    //     return enseignantDAO.update(id, enseignant2);
    // }

    // public void supprimer(Long id){
    //      enseignantDAO.delete(id);
    // }
    // //Liste de tous les enseignants
    // public List<Enseignant> afficherEnseignants(){
    //     return enseignantDAO.findAll();
    // }

    // public Enseignant trouverEnseignant(Long id){
    //     return enseignantDAO.findById(id);
    // }

    // //Liste des UEs pour un Enseignant donne 
    // public List<UE> afficherUEsParEnseignant(Long enseignantId) {
    //     Enseignant enseignant = enseignantDAO.findById(enseignantId);
    //     return enseignant != null ? enseignant.getUeList() : new ArrayList<>();
    // }

}

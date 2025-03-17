package sn.uasz.m1.inscription.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sn.uasz.m1.inscription.dao.GroupeDAO;
import sn.uasz.m1.inscription.dao.InscriptionDAO;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;

public class RepartitionService {
    private final GroupeDAO groupeDAO;
    private final InscriptionDAO inscriptionDAO;
    private final FormationService formationService;

    public RepartitionService() {
        this.groupeDAO = new GroupeDAO();
        this.inscriptionDAO = new InscriptionDAO();
        this.formationService = new FormationService();
    }

    /**
     * Répartit les étudiants d'une formation en respectant l'ordre alphabétique.
     * Si des groupes existent, on fusionne d'abord les étudiants avant d'en créer de nouveaux.
     */
    // public void repartirEtudiants(Long formationId, int tailleGroupeTD, int tailleGroupeTP) {
    //     List<Etudiant> etudiants = inscriptionDAO.findEtudiantsByFormation(formationId);
    //     if (etudiants.isEmpty()) {
    //         throw new RuntimeException("Aucun étudiant inscrit dans cette formation.");
    //     }

    //     // Trier tous les étudiants (y compris ceux déjà répartis)
    //     etudiants.sort(Comparator.comparing(Etudiant::getNom).thenComparing(Etudiant::getPrenom));

    //     // Récupérer les étudiants déjà répartis
    //     List<Etudiant> etudiantsRepartis = groupeDAO.findEtudiantsRepartis(formationId);

    //     // Déterminer les nouveaux étudiants
    //     List<Etudiant> nouveauxEtudiants = new ArrayList<>(etudiants);
    //     nouveauxEtudiants.removeAll(etudiantsRepartis);

    //     if (nouveauxEtudiants.isEmpty()) {
    //         System.out.println("✅ Aucun nouvel étudiant à répartir.");
    //         return;
    //     }

    //     System.out.println("📌 Nouveaux étudiants à répartir : " + nouveauxEtudiants.size());

    //     // Répartition dans les groupes existants
    //     fusionnerEtudiantsDansGroupes(formationId, TypeGroupe.TD, nouveauxEtudiants, tailleGroupeTD);
    //     fusionnerEtudiantsDansGroupes(formationId, TypeGroupe.TP, nouveauxEtudiants, tailleGroupeTP);
    // }

    // /**
    //  * Fusionne les étudiants dans les groupes existants et crée un nouveau groupe uniquement si nécessaire.
    //  */
    // private void fusionnerEtudiantsDansGroupes(Long formationId, TypeGroupe type, List<Etudiant> nouveauxEtudiants, int tailleMax) {
    //     List<Groupe> groupes = groupeDAO.findGroupesByFormation(formationId, type);
    //     int index = 0;

    //     for (Etudiant etudiant : nouveauxEtudiants) {
    //         boolean ajoute = false;

    //         // Chercher un groupe avec de la place
    //         for (Groupe groupe : groupes) {
    //             if (groupe.getEtudiants().size() < tailleMax) {
    //                 groupe.getEtudiants().add(etudiant);
    //                 groupeDAO.merge(groupe);
    //                 ajoute = true;
    //                 break;
    //             }
    //         }

    //         // Si aucun groupe n'avait de place, on en crée un nouveau
    //         if (!ajoute) {
    //             Groupe nouveauGroupe = new Groupe();
    //             nouveauGroupe.setType(type);
    //             nouveauGroupe.setFormation(formationService.getFormationById(formationId));
    //             nouveauGroupe.getEtudiants().add(etudiant);
    //             groupeDAO.save(nouveauGroupe);
    //             groupes.add(nouveauGroupe);
    //         }
    //     }
    // }

    public void repartirEtudiants(Long formationId) {
        List<Etudiant> etudiants = inscriptionDAO.findEtudiantsByFormation(formationId);
        if (etudiants.isEmpty()) {
            throw new RuntimeException("Aucun étudiant inscrit dans cette formation.");
        }
    
        // Trier tous les étudiants (y compris ceux déjà répartis)
        etudiants.sort(Comparator.comparing(Etudiant::getNom).thenComparing(Etudiant::getPrenom));
    
        // Récupérer les étudiants déjà répartis
        List<Etudiant> etudiantsRepartis = groupeDAO.findEtudiantsRepartis(formationId);
    
        // Déterminer les nouveaux étudiants
        List<Etudiant> nouveauxEtudiants = new ArrayList<>(etudiants);
        nouveauxEtudiants.removeAll(etudiantsRepartis);
    
        if (nouveauxEtudiants.isEmpty()) {
            System.out.println("✅ Aucun nouvel étudiant à répartir.");
            return;
        }
    
        System.out.println("📌 Nouveaux étudiants à répartir : " + nouveauxEtudiants.size());
    
        // Répartition dans les groupes existants pour les types TD et TP
        fusionnerEtudiantsDansGroupes(formationId, TypeGroupe.TD, nouveauxEtudiants);
        fusionnerEtudiantsDansGroupes(formationId, TypeGroupe.TP, nouveauxEtudiants);
    }
    
    /**
     * Fusionne les étudiants dans les groupes existants et crée un nouveau groupe uniquement si nécessaire.
     */
    // private void fusionnerEtudiantsDansGroupes(Long formationId, TypeGroupe type, List<Etudiant> nouveauxEtudiants) {
    //     List<Groupe> groupes = groupeDAO.findGroupesByFormation(formationId, type);
    //     int index = 0;
    
    //     // Récupérer la capacité maximale pour chaque groupe existant
    //     for (Etudiant etudiant : nouveauxEtudiants) {
    //         boolean ajoute = false;
    
    //         // Chercher un groupe avec de la place
    //         for (Groupe groupe : groupes) {
    //             int capaciteMax = groupe.getCapacite();  // Récupérer la capacité maximale du groupe
    //             if (groupe.getEtudiants().size() < capaciteMax) {
    //                 groupe.getEtudiants().add(etudiant);
    //                 groupeDAO.merge(groupe);
    //                 ajoute = true;
    //                 break;
    //             }
    //         }
    
    //         // Si aucun groupe n'avait de place, on en crée un nouveau
    //         if (!ajoute) {
    //             Groupe nouveauGroupe = new Groupe();
    //             nouveauGroupe.setType(type);
    //             nouveauGroupe.setFormation(formationService.getFormationById(formationId));
    //             nouveauGroupe.getEtudiants().add(etudiant);
    //             // Assurez-vous de définir la capacité maximale du nouveau groupe en fonction du type de groupe
    //             int capaciteMax = determineCapaciteMax(type);  // Cette méthode détermine la capacité du groupe en fonction du type (TD ou TP)
    //             nouveauGroupe.setCapacite(capaciteMax);
    //             groupeDAO.save(nouveauGroupe);
    //             groupes.add(nouveauGroupe);
    //         }
    //     }
    // }

    private void fusionnerEtudiantsDansGroupes(Long formationId, TypeGroupe type, List<Etudiant> nouveauxEtudiants) {
        List<Groupe> groupes = groupeDAO.findGroupesByFormation(formationId, type);
    
        for (Etudiant etudiant : nouveauxEtudiants) {
            boolean ajoute = false;
    
            // Chercher un groupe avec de la place
            for (Groupe groupe : groupes) {
                int capaciteMax = groupe.getCapacite();  // Récupérer la capacité maximale du groupe
    
                // Vérifier si l'étudiant est déjà dans le groupe
                if (!groupe.getEtudiants().contains(etudiant) && groupe.getEtudiants().size() < capaciteMax) {
                    groupe.getEtudiants().add(etudiant);
                    groupeDAO.merge(groupe); // Enregistrer les modifications du groupe
                    ajoute = true;
                    break;
                }
            }
    
            // Si aucun groupe n'avait de place, on en crée un nouveau
            if (!ajoute) {
                Groupe nouveauGroupe = new Groupe();
                nouveauGroupe.setType(type);
                Formation formation = formationService.getFormationById(formationId);
                nouveauGroupe.setFormation(formation);
                int capaciteMax = determineCapaciteMax(type); // Cette méthode détermine la capacité du groupe en fonction du type (TD ou TP)
                nouveauGroupe.setCapacite(capaciteMax);
                nouveauGroupe.getEtudiants().add(etudiant);
    
                groupeDAO.save(nouveauGroupe);
                groupes.add(nouveauGroupe);
            }
        }
    }
    
    
    /**
     * Détermine la capacité maximale pour le groupe en fonction de son type (TD ou TP).
     * 
     * @param type Le type du groupe (TD ou TP)
     * @return La capacité maximale du groupe
     */
    private int determineCapaciteMax(TypeGroupe type) {
        // Logique pour définir la capacité maximale en fonction du type de groupe (par exemple, 30 pour TD, 20 pour TP)
        if (type == TypeGroupe.TD) {
            return 30; // Exemple de capacité pour les TD
        } else if (type == TypeGroupe.TP) {
            return 30; // Exemple de capacité pour les TP
        }
        return 0; // Valeur par défaut
    }
    
}

package sn.uasz.m1.inscription.fixtures;

import java.time.LocalDate;
import java.util.*;

import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.enumeration.Sexe;
import sn.uasz.m1.inscription.service.EtudiantService;

public class EtudiantLoader {
    private final EtudiantService etudiantService = new EtudiantService();

    public void loadEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        Random random = new Random();
    
        // Liste des étudiants avec sexe sous forme de String
        String[][] etudiantData = {
            {"Penda", "BA", "p.b1@zig.univ.sn", "FEMME"},
            {"Alioune", "BALDE", "ad.b@zig.univ.sn", "HOMME"},
            {"Abdoulaye", "DAFFE", "a.daffe20170696@zig.univ.sn", "HOMME"},
            {"Dieydy", "DEMBELE", "d.dembele20170697@zig.univ.sn", "HOMME"},
            {"Aimerou", "DIA", "a.d242@zig.univ.sn", "HOMME"},
            {"Moussa", "DIA", "m.d199@zig.univ.sn", "HOMME"},
            {"Madicke", "DIA", "m.d579@zig.univ.sn", "HOMME"},
            {"Francois Insa", "DIADHIOU", "fi.d@zig.univ.sn", "HOMME"},
            {"Abdourahmane", "DIALLO", "a.d255@zig.univ.sn", "HOMME"},
            {"Dieynaba", "DIALLO", "d.d60@zig.univ.sn", "FEMME"},
            {"Safietou", "DIALLO", "s.d81@zig.univ.sn", "FEMME"},
            {"Djim", "DIASSE", "d.d57@zig.univ.sn", "HOMME"},
            {"Awa Marina", "DIEME", "am.d10@zig.univ.sn", "FEMME"},
            {"Isseulmou", "DIENG", "i.d47@zig.univ.sn", "HOMME"},
            {"Aliou", "DIOP", "a.d230@zig.univ.sn", "HOMME"},
            {"Mamadou Moustapha", "DIOP", "mm.d19@zig.univ.sn", "HOMME"},
            {"Maniang", "DIOP", "a.d58@zig.univ.sn", "HOMME"},
            {"El Hadji Abdou", "DRAME", "eha.d1@zig.univ.sn", "HOMME"},
            {"Djiby", "FALL", "d.f16@zig.univ.sn", "HOMME"},
            {"Marame", "FALL", "m.f46@zig.univ.sn", "FEMME"},
            {"Abdoulaye", "GAYE", "a.g42@zig.univ.sn", "HOMME"},
            {"Mouhamad Nassour Cherif", "KANE", "mnc.k@zig.univ.sn", "HOMME"},
            {"Ansoumana", "KONATE", "a.k47@zig.univ.sn", "HOMME"},
            {"Mouhamadou Mansour", "MBOUP", "mm.m1@zig.univ.sn", "HOMME"},
            {"Mouhamad Sagnane", "NDIAYE", "ms.n3@zig.univ.sn", "HOMME"},
            {"Seydina Mouhamad Al Hamine", "NDIAYE", "smah.n@zig.univ.sn", "HOMME"},
            {"Ndoya", "NDONG", "n.n16@zig.univ.sn", "FEMME"},
            {"Mouhamed", "NDOYE", "m.n115@zig.univ.sn", "HOMME"},
            {"Abdoulaye", "NIANG", "a.n102@zig.univ.sn", "HOMME"},
        };
    
        for (String[] data : etudiantData) {
            Etudiant etudiant = new Etudiant();
            etudiant.setPrenom(data[0]);
            etudiant.setNom(data[1]);
            etudiant.setEmail(data[2]);
    
            // ✅ Correction : Convertir String en Enum Sexe
            etudiant.setSexe(Sexe.valueOf(data[3]));
    
            etudiant.setMotDePasse("passer123");
    
            // Générer un INE unique (9 chiffres)
            etudiant.setIne(String.format("%09d", random.nextInt(1000000000)));
    
            // Générer une date de naissance aléatoire entre 1997 et 2005
            int year = 1997 + random.nextInt(9); 
            int month = 1 + random.nextInt(12);
            int day = 1 + random.nextInt(28);
            etudiant.setDateNaissance(LocalDate.of(year, month, day));
    
            // Adresse fictive
            etudiant.setAdresse("Rue " + (random.nextInt(50) + 1) + ", Ziguinchor");
    
            etudiants.add(etudiant);
        }
    
        // Sauvegarde en base de données
        for (Etudiant etu : etudiants) {
            etudiantService.createEtudiant(etu);
        }
    
        System.out.println("✅ " + etudiants.size() + " étudiants ont été ajoutés avec succès !");
    }
    

}

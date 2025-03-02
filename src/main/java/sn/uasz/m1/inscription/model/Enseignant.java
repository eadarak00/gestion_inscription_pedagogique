package sn.uasz.m1.inscription.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    
    public Enseignant(String nom, String prenom, String email, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
    }

    // @OneToMany(mappedBy = "enseignant")
    // private List<UE> ueList= new ArrayList<>();
}

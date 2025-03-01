package sn.uasz.m1.inscription.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;

    @OneToMany(mappedBy = "enseignant")
    private List<Ue> ueList= new ArrayList<>();
}

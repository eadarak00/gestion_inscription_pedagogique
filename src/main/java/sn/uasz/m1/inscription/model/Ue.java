package sn.uasz.m1.inscription.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ue {
    private long id;
    private String code;
    private String nom;
    private int volumeHoraire;
    private int coefficient;
    private int credit;
    private boolean obligatoire;

    @ManyToOne
    @JoinColumn(name="id")
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name= "formationId")
    private Formation formation;

}

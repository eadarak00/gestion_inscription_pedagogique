package sn.uasz.m1.inscription.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.uasz.m1.inscription.model.enumeration.StatutInscription;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "inscription_ue_optionnelle",
     joinColumns = @JoinColumn(name = "inscription_id"), 
     inverseJoinColumns = @JoinColumn(name = "ue_id"))
    private List<UE> uesOptionnelles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ue_inscrites", 
    joinColumns = @JoinColumn(name = "inscription_id"),
     inverseJoinColumns = @JoinColumn(name = "ue_id"))
    private List<UE> ues = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatutInscription statut = StatutInscription.EN_ATTENTE;

}

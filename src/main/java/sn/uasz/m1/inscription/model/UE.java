package sn.uasz.m1.inscription.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 7)
    private String code;
    private String libelle;
    private int volumeHoraire;
    private int coefficient;
    private int credit;
    private boolean obligatoire = true;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "formationId")
    private Formation formation = null;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ResponsablePedagogique responsable;
}

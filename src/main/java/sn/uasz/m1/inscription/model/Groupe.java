package sn.uasz.m1.inscription.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacite;
    @Enumerated(EnumType.STRING)
    private TypeGroupe type;

    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;
}

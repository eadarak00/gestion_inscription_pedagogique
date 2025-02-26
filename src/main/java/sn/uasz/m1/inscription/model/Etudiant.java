package sn.uasz.m1.inscription.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.uasz.m1.inscription.model.enumeration.Sexe;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant extends Utilisateur {
    @Column(unique = true, length = 9)
    private String ine;
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private String adresse;
}

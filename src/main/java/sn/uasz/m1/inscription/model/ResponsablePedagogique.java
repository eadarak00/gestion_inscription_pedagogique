package sn.uasz.m1.inscription.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResponsablePedagogique extends Utilisateur {
    private String departement;

    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL)
    private List<Formation> formations = new ArrayList<>();
    
    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL)
    private List<UE> ues = new ArrayList<>();

}

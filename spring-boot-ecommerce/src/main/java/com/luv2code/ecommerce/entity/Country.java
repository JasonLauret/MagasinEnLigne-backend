package com.luv2code.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id; // Identifiant unique du pays

    @Column(name="code")
    @Size(min = 2, max = 2, message = "Le code doit avoir exactement 2 caractères")
    private String code; // Code du pays, souvent utilisé pour identification

    @Column(name="name")
    @Size(min = 2, message = "Le nom doit avoir au moins 2 caractères")
    private String name; // Nom du pays

    // Relation One-to-Many avec State
    @OneToMany(mappedBy = "country") // Indique que plusieurs états sont liés à ce pays
    @JsonIgnore // Ignore le champs, pour ne pas le récupérer quand j'affiche une Country
    private List<State> states; // Liste des états appartenant à ce pays
}
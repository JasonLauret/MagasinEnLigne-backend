package com.magasinEnLigne.ecommerce.entity;

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
    private int id;

    @Column(name="code")
    @Size(min = 2, max = 2, message = "Le code doit avoir 2 caractères")
    private String code;

    @Column(name="name")
    @Size(min = 2, message = "Le nom doit avoir au moins 2 caractères")
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;
}
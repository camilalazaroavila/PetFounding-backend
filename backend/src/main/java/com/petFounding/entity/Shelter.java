package com.petFounding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelter")
@Getter
@Setter
@NoArgsConstructor
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_refugio", nullable = false)
    private String nombreRefugio;

    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "refugio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pet> mascotas = new ArrayList<>();

    @OneToMany(mappedBy = "refugio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Donation> donaciones = new ArrayList<>();

    @OneToMany(mappedBy = "refugio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FinancialReport> reportesFinancieros = new ArrayList<>();

    @OneToMany(mappedBy = "refugio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AdoptionReport> reportesAdopciones = new ArrayList<>();

    public Shelter(String nombreRefugio, String direccion) {
        this.nombreRefugio = nombreRefugio;
        this.direccion = direccion;
    }
}

package com.petFounding.entity;

import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private Integer edad;
    private String descripcion;
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    private Sex sexo;

    @Enumerated(EnumType.STRING)
    private Size tamano;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus estadoAdopcion;

    private Boolean esterilizado;
    private Boolean vacunado;

    @ManyToOne
    @JoinColumn(name = "id_refugio")
    private Shelter refugio;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    private List<AdoptionApplication> solicitudes = new ArrayList<>();

    public Pet(String nombre, String raza, Integer edad, String descripcion, Sex sexo, Size tamano) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.descripcion = descripcion;
        this.sexo = sexo;
        this.tamano = tamano;
        this.fechaIngreso = LocalDate.now();
        this.estadoAdopcion = AdoptionStatus.DISPONIBLE;
        this.esterilizado = false;
        this.vacunado = false;
    }
}
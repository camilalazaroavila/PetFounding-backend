package com.petFounding.entity;

import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import com.petFounding.valid.PetValid;
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

    public Pet(PetValid datos) {
        this.nombre = datos.nombre();
        this.raza = datos.raza();
        this.edad = datos.edad();
        this.descripcion = datos.descripcion();
        this.fechaIngreso = datos.fechaIngreso();
        this.sexo = datos.sexo();
        this.tamano = datos.tamano();
        this.estadoAdopcion = datos.estadoAdopcion();
        this.esterilizado = datos.esterilizado();
        this.vacunado = datos.vacunado();
        this.refugio = datos.refugio();
    }

    public void actualizarDatos(PetValid datos) {
        if (datos.nombre() != null) this.nombre = datos.nombre();
        if (datos.raza() != null) this.raza = datos.raza();
        if (datos.edad() != null) this.edad = datos.edad();
        if (datos.descripcion() != null) this.descripcion = datos.descripcion();
        if (datos.sexo() != null) this.sexo = datos.sexo();
        if (datos.tamano() != null) this.tamano = datos.tamano();
        if (datos.estadoAdopcion() != null) this.estadoAdopcion = datos.estadoAdopcion();
        if (datos.esterilizado() != null) this.esterilizado = datos.esterilizado();
        if (datos.vacunado() != null) this.vacunado = datos.vacunado();
        if (datos.refugio() != null) this.refugio = datos.refugio();
    }
}
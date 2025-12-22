package com.petFounding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdoptionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaSolicitud;
    private String motivacion;
    private String experienciaPrevia;
    private String condicionesVivienda;
    private LocalDate fechaRta;
    private String comentarioRefugio;

    @ManyToOne
    @JoinColumn(name = "id_adoptante")
    private User adoptante;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Pet mascota;

    public AdoptionApplication(String motivacion, String experienciaPrevia, String condicionesVivienda) {
        this.fechaSolicitud = LocalDate.now();
        this.motivacion = motivacion;
        this.experienciaPrevia = experienciaPrevia;
        this.condicionesVivienda = condicionesVivienda;
    }
}
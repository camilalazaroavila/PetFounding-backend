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
public class AdoptionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate periodo;
    private Integer mascotasAdoptadas;
    private Integer solicitudesPendientes;
    private Integer solicitudesAprobadas;
    private Integer solicitudesRechazadas;

    @ManyToOne
    @JoinColumn(name = "id_refugio")
    private Shelter refugio;

    public AdoptionReport(LocalDate periodo, Integer mascotasAdoptadas, Integer solicitudesPendientes,
                          Integer solicitudesAprobadas, Integer solicitudesRechazadas) {
        this.periodo = periodo;
        this.mascotasAdoptadas = mascotasAdoptadas;
        this.solicitudesPendientes = solicitudesPendientes;
        this.solicitudesAprobadas = solicitudesAprobadas;
        this.solicitudesRechazadas = solicitudesRechazadas;
    }
}
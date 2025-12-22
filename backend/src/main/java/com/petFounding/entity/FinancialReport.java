package com.petFounding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FinancialReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal totalRecaudado;
    private Integer cantidadDonaciones;

    @ManyToOne
    @JoinColumn(name = "id_refugio")
    private Shelter refugio;

    public FinancialReport(LocalDate fechaInicio, LocalDate fechaFin, BigDecimal totalRecaudado, Integer cantidadDonaciones) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalRecaudado = totalRecaudado;
        this.cantidadDonaciones = cantidadDonaciones;
    }
}
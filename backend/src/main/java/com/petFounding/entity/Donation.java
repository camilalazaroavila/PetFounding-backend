package com.petFounding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;
    private LocalDate fecha;
    private String transaccionId;
    private String metodoPago;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    @OneToMany(mappedBy = "donacion", cascade = CascadeType.ALL)
    private List<PaymentGateway> pasarelas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_refugio")
    private Shelter refugio;

    public Donation(BigDecimal monto, LocalDate fecha, String metodoPago, String comentario) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.comentario = comentario;
    }
}
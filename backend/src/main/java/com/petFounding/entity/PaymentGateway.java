package com.petFounding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentGateway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mercadoPagoToken;
    private String paymentId;
    private String idDe;

    @ManyToOne
    @JoinColumn(name = "id_donacion")
    private Donation donacion;

    public PaymentGateway(String mercadoPagoToken, String paymentId, String idDe) {
        this.mercadoPagoToken = mercadoPagoToken;
        this.paymentId = paymentId;
        this.idDe = idDe;
    }
}
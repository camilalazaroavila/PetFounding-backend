package com.petFounding.interfacee;

import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;

import java.util.List;

public interface PaymentGatewayService {
    PaymentGateway crearPreferencia(PaymentGateway pasarela);
    PaymentGateway procesarPago(String paymentId);
    PaymentGateway verificarEstado(String paymentId);
    PaymentGateway obtenerHistorial(Long id);
    List<PaymentGateway> obtenerPorDonacion(Donation donacion);
}

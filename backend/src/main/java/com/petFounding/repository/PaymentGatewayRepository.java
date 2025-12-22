package com.petFounding.repository;

import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;

import java.util.List;

public interface PaymentGatewayRepository {
    PaymentGateway guardar(PaymentGateway pasarela);
    PaymentGateway modificar(PaymentGateway pasarela);
    void eliminar(Long id);
    PaymentGateway buscarPorId(Long id);
    PaymentGateway buscarPorPaymentId(String paymentId);
    List<PaymentGateway> buscarPorDonacion(Donation donacion);
    List<PaymentGateway> buscarTodos();
}

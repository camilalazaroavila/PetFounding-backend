package com.petFounding.service;

import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;
import com.petFounding.interfaceService.PaymentGatewayService;
import com.petFounding.repository.PaymentGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("paymentGatewayService")
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private final PaymentGatewayRepository paymentGatewayRepository;

    @Autowired
    public PaymentGatewayServiceImpl(PaymentGatewayRepository paymentGatewayRepository) {
        this.paymentGatewayRepository = paymentGatewayRepository;
    }

    @Override
    public PaymentGateway crearPreferencia(PaymentGateway pasarela) {
        // TODO: Integración con Mercado Pago SDK
        // 1. Crear PreferenceClient
        // 2. Ejecutar client.create(request)
        // 3. pasarela.setPaymentId(response.getId())

        // Adaptado: save() reemplaza a guardar()
        return paymentGatewayRepository.save(pasarela);
    }

    @Override
    public PaymentGateway procesarPago(String paymentId) {
        // Adaptado: findByPaymentId con Optional
        PaymentGateway pasarela = paymentGatewayRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Pasarela no encontrada con ID: " + paymentId));

        // TODO: Consultar SDK de Mercado Pago para validar estado (approved, pending, rejected)

        // Adaptado: save() reemplaza a modificar()
        return paymentGatewayRepository.save(pasarela);
    }

    @Override
    public PaymentGateway verificarEstado(String paymentId) {
        // Adaptado: findByPaymentId
        return paymentGatewayRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Pasarela no encontrada"));
    }

    @Override
    public PaymentGateway obtenerHistorial(Long id) {
        /* Nota: getReferenceById es para carga perezosa (Lazy).
           Para obtener los datos reales y validar existencia, usamos findById.
        */
        return paymentGatewayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasarela no encontrada"));
    }

    @Override
    public List<PaymentGateway> obtenerPorDonacion(Donation donacion) {
        return paymentGatewayRepository.findByDonacion(donacion);
    }
}
package com.petFounding.service;


import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;
import com.petFounding.interfacee.PaymentGatewayService;
import com.petFounding.repository.PaymentGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("paymentGatewayService")
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private PaymentGatewayRepository paymentGatewayRepository;

    @Autowired
    public PaymentGatewayServiceImpl(PaymentGatewayRepository paymentGatewayRepository) {
        this.paymentGatewayRepository = paymentGatewayRepository;
    }

    @Override
    public PaymentGateway crearPreferencia(PaymentGateway pasarela) {
        // TODO: Implementar integración con Mercado Pago
        // 1. Crear preferencia de pago en Mercado Pago
        // 2. Obtener el token de la preferencia
        // 3. Guardar los datos de la pasarela
        return paymentGatewayRepository.guardar(pasarela);
    }

    @Override
    public PaymentGateway procesarPago(String paymentId) {
        // TODO: Implementar procesamiento de pago
        // 1. Buscar la pasarela por paymentId
        PaymentGateway pasarela = paymentGatewayRepository.buscarPorPaymentId(paymentId);
        if (pasarela == null) {
            throw new RuntimeException("Pasarela no encontrada");
        }
        // 2. Consultar el estado del pago en Mercado Pago
        // 3. Actualizar el estado de la pasarela
        // 4. Si el pago fue exitoso, confirmar la donación asociada
        return paymentGatewayRepository.modificar(pasarela);
    }

    @Override
    public PaymentGateway verificarEstado(String paymentId) {
        // TODO: Implementar verificación de estado
        // 1. Buscar la pasarela por paymentId
        PaymentGateway pasarela = paymentGatewayRepository.buscarPorPaymentId(paymentId);
        if (pasarela == null) {
            throw new RuntimeException("Pasarela no encontrada");
        }
        // 2. Consultar el estado actual en Mercado Pago
        // 3. Actualizar el estado localmente si cambió
        return pasarela;
    }

    @Override
    public PaymentGateway obtenerHistorial(Long id) {
        // TODO: Implementar obtención de historial
        // 1. Buscar la pasarela por ID
        PaymentGateway pasarela = paymentGatewayRepository.buscarPorId(id);
        if (pasarela == null) {
            throw new RuntimeException("Pasarela no encontrada");
        }
        // 2. Obtener todos los movimientos asociados
        return pasarela;
    }

    @Override
    public List<PaymentGateway> obtenerPorDonacion(Donation donacion) {
        return paymentGatewayRepository.buscarPorDonacion(donacion);
    }
}

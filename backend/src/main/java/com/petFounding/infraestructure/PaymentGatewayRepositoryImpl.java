package com.petFounding.infraestructure;

import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;
import com.petFounding.repository.PaymentGatewayRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("paymentGatewayRepository")
public class PaymentGatewayRepositoryImpl implements PaymentGatewayRepository {
// no se como plantearlol y si deberia estar aca o en servicio
    private SessionFactory sessionFactory;

    @Autowired
    public PaymentGatewayRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
//TODO: Por el momento no hacer este!!!!!!
    @Override
    public PaymentGateway guardar(PaymentGateway pasarela) {
        return null;
    }

    @Override
    public PaymentGateway modificar(PaymentGateway pasarela) {

        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public PaymentGateway buscarPorId(Long id) {

        return null;
    }

    @Override
    public PaymentGateway buscarPorPaymentId(String paymentId) {

        return null;
    }

    @Override
    public List<PaymentGateway> buscarPorDonacion(Donation donacion) {

        return null;
    }

    @Override
    public List<PaymentGateway> buscarTodos() {

        return null;
    }
}
package com.petFounding.repository;

import com.petFounding.entity.Donation;
import com.petFounding.entity.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentGatewayRepository extends JpaRepository<PaymentGateway, Long> {
    Optional<PaymentGateway> findByPaymentId(String paymentId);
    List<PaymentGateway> findByDonacion(Donation donacion);
}
package com.petFounding.service;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import com.petFounding.interfaceService.DonationService;
import com.petFounding.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service("donationService")
@Transactional
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public Donation processar(Donation donacion) {
        if (donacion.getMonto() == null || donacion.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto de la donación debe ser mayor a cero");
        }

        donacion.setFecha(LocalDate.now());
        return donationRepository.save(donacion);
    }

    @Override
    public Donation confirmar(Long id) {
        Donation donacion = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));

        // Aquí iría la lógica con la pasarela (ej: Mercado Pago)
        // donacion.setEstado(EstadoDonacion.CONFIRMADA);

        return donationRepository.save(donacion);
    }

    @Override
    public Donation cancelar(Long id) {
        Donation donacion = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));

        return donationRepository.save(donacion);
    }

    @Override
    public Donation generarRecibo(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
    }

    @Override
    public List<Donation> obtenerDonacionesPorUsuario(User usuario) {
        return donationRepository.findByUsuario(usuario);
    }

    @Override
    public List<Donation> obtenerDonacionesPorRefugio(Shelter refugio) {
        return donationRepository.findByRefugio(refugio);
    }

    @Override
    public List<Donation> obtenerDonacionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return donationRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    @Override
    public BigDecimal calcularTotalRecaudado(Shelter refugio) {
        List<Donation> donaciones = donationRepository.findByRefugio(refugio);

        return donaciones.stream()
                .map(Donation::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
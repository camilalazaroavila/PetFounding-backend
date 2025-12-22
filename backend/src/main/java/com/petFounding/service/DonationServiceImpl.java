package com.petFounding.service;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import com.petFounding.interfacee.DonationService;
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

    private DonationRepository donationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public Donation processar(Donation donacion) {
        // TODO: Implementar la logica d la donacion
        // 1 mono > 0
        // 2 generar Id con la ransaccion d l momento
        // 3 guadar fecha
        donacion.setFecha(LocalDate.now());
        // 4. guardar donacion en bd
        return donationRepository.guardar(donacion);
    }

    @Override
    public Donation confirmar(Long id) {
        // TODO:logica d confirmacion
        // 1 buscar por id
        Donation donacion = donationRepository.buscarPorId(id);
        // 2 cheque el pago verificado con la pasarela d pago
        // 3 set el estado de la donacion
        // 4 en el controller mandar msj de confirmacion
        return donacion;
    }

    @Override
    public Donation cancelar(Long id) {
        // TODO: cancelar/eliminar
        // 1 buscamos por id
        Donation donacion = donationRepository.buscarPorId(id);
        // 2 cheuqeamos q exirrta
        // 3 y endria haber un reembolso pero ni idea
        // 4 set estado d donacion
        return donacion;
    }

    @Override
    public Donation generarRecibo(Long id) {
        // TODO: opcional, pq mp ya t lo da
        Donation donacion = donationRepository.buscarPorId(id);
        if (donacion == null) {
            throw new RuntimeException("Donación no encontrada");
        }
        return donacion;
    }

    @Override
    public List<Donation> obtenerDonacionesPorUsuario(User usuario) {
        return donationRepository.buscarPorUsuario(usuario);
    }

    @Override
    public List<Donation> obtenerDonacionesPorRefugio(Shelter refugio) {
        return donationRepository.buscarPorRefugio(refugio);
    }

    @Override
    public List<Donation> obtenerDonacionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return donationRepository.buscarPorFecha(fechaInicio, fechaFin);
    }

    @Override
    public BigDecimal calcularTotalRecaudado(Shelter refugio) {
        // TODO: total
        // 1 buscamos por id o buscamos un array de las donciones y llamamos a los numeros para sumar
        List<Donation> donaciones = donationRepository.buscarPorRefugio(refugio);
        // 2 sumar todos
        BigDecimal total = BigDecimal.ZERO;
        for (Donation donacion : donaciones) {
            total = total.add(donacion.getMonto());
        }
        // 3 return resultado
        return total;
    }
}
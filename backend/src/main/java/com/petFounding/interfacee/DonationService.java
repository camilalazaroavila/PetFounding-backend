package com.petFounding.interfacee;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonationService {
    Donation processar(Donation donacion);
    Donation confirmar(Long id);
    Donation cancelar(Long id);
    Donation generarRecibo(Long id);
    List<Donation> obtenerDonacionesPorUsuario(User usuario);
    List<Donation> obtenerDonacionesPorRefugio(Shelter refugio);
    List<Donation> obtenerDonacionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    BigDecimal calcularTotalRecaudado(Shelter refugio);
}


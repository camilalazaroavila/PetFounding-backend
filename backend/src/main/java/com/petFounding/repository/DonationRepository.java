package com.petFounding.repository;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface DonationRepository {
    Donation guardar(Donation donacion);
    Donation modificar(Donation donacion);
    void eliminar(Long id);
    Donation buscarPorId(Long id);
    List<Donation> buscarPorUsuario(User usuario);
    List<Donation> buscarPorRefugio(Shelter refugio);
    List<Donation> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<Donation> buscarTodos();
}
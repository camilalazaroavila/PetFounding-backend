package com.petFounding.interfacee;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import java.util.List;

public interface ShelterService {

    Shelter crearRefugio(Shelter refugio);

    Shelter actualizarRefugio(Long id, Shelter refugio);

    void eliminarRefugio(Long id);

    Shelter obtenerRefugioPorId(Long id);

    Shelter obtenerRefugioPorNombre(String nombre);

    List<Shelter> obtenerTodosLosRefugios();

    Pet crearMascota(Pet mascota, Long idRefugio);

    Pet actualizarMascota(Long idMascota, Pet mascota);

    void eliminarMascota(Long idMascota);

    List<Pet> gestionarSolicitudes(Long idRefugio);

    void verReportes(Long idRefugio);
}

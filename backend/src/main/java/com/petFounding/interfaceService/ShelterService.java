package com.petFounding.interfaceService;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.valid.ShelterValid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShelterService {
    Shelter crearRefugio(Shelter refugio);
    Shelter actualizarRefugio(Long id, ShelterValid refugio);
    void eliminarRefugio(Long id);
    Shelter obtenerRefugioPorId(Long id);
    Shelter obtenerRefugioPorNombre(String nombre);
    Page<Shelter> obtenerTodosLosRefugios(Pageable paginacion);
    List<Pet> gestionarSolicitudes(Long idRefugio);
    void verReportes(Long idRefugio);
}

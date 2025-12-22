package com.petFounding.interfacee;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;

import java.util.List;

public interface AdoptionApplicationService {
    AdoptionApplication crear(AdoptionApplication solicitud);
    AdoptionApplication aprobar(Long id);
    AdoptionApplication rechazar(Long id, String comentario);
    void cancelar(Long id);
    AdoptionApplication obtenerPorId(Long id);
    List<AdoptionApplication> obtenerSolicitudesPorAdoptante(User adoptante);
    List<AdoptionApplication> obtenerSolicitudesPorMascota(Pet mascota);
    Boolean existeSolicitud(User adoptante, Pet mascota);
}

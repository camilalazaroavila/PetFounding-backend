package com.petFounding.interfaceService;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;
import com.petFounding.valid.AdoptionApplicationValid;

import java.util.List;

public interface AdoptionApplicationService {
    AdoptionApplication crear(AdoptionApplicationValid solicitud);
    AdoptionApplication aprobar(Long id);
    AdoptionApplication rechazar(Long id, String comentario);
    void cancelar(Long id);
    AdoptionApplication obtenerPorId(Long id);
    List<AdoptionApplication> obtenerSolicitudesPorAdoptante(User adoptante);
    List<AdoptionApplication> obtenerSolicitudesPorMascota(Pet mascota);
    Boolean existeSolicitud(User adoptante, Pet mascota);
}

package com.petFounding.service;


import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.interfacee.AdoptionApplicationService;
import com.petFounding.repository.AdoptionApplicationRepository;
import com.petFounding.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("adoptionApplicationService")
@Transactional
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private AdoptionApplicationRepository adoptionApplicationRepository;
    private PetRepository petRepository;

    @Autowired
    public AdoptionApplicationServiceImpl(AdoptionApplicationRepository adoptionApplicationRepository,
                                          PetRepository petRepository) {
        this.adoptionApplicationRepository = adoptionApplicationRepository;
        this.petRepository = petRepository;
    }

    @Override
    public AdoptionApplication crear(AdoptionApplication solicitud) {
        if (adoptionApplicationRepository.existePorAdoptanteYMascota(
                solicitud.getAdoptante(), solicitud.getMascota())) {
            throw new RuntimeException("Ya existe una solicitud para esta mascota");
        }

        solicitud.setFechaSolicitud(LocalDate.now());
        return adoptionApplicationRepository.guardar(solicitud);
    }

    @Override
    public AdoptionApplication aprobar(Long id) {
        AdoptionApplication solicitud = adoptionApplicationRepository.buscarPorId(id);

        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        solicitud.setFechaRta(LocalDate.now());

        Pet mascota = solicitud.getMascota();
        mascota.setEstadoAdopcion(AdoptionStatus.ADOPTADO);
        petRepository.save(mascota);


        return adoptionApplicationRepository.modificar(solicitud);
    }

    @Override
    public AdoptionApplication rechazar(Long id, String comentario) {
        AdoptionApplication solicitud = adoptionApplicationRepository.buscarPorId(id);

        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        solicitud.setFechaRta(LocalDate.now());
        solicitud.setComentarioRefugio(comentario);

        return adoptionApplicationRepository.modificar(solicitud);
    }

    @Override
    public void cancelar(Long id) {
        AdoptionApplication solicitud = adoptionApplicationRepository.buscarPorId(id);

        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        adoptionApplicationRepository.eliminar(id);
    }

    @Override
    public AdoptionApplication obtenerPorId(Long id) {
        AdoptionApplication solicitud = adoptionApplicationRepository.buscarPorId(id);

        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        return solicitud;
    }

    @Override
    public List<AdoptionApplication> obtenerSolicitudesPorAdoptante(User adoptante) {
        return adoptionApplicationRepository.buscarPorAdoptante(adoptante);
    }

    @Override
    public List<AdoptionApplication> obtenerSolicitudesPorMascota(Pet mascota) {
        return adoptionApplicationRepository.buscarPorMascota(mascota);
    }

    @Override
    public Boolean existeSolicitud(User adoptante, Pet mascota) {
        return adoptionApplicationRepository.existePorAdoptanteYMascota(adoptante, mascota);
    }
}
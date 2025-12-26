package com.petFounding.service;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.infraestructure.exception.SolicitudDuplicadaException;
import com.petFounding.interfaceService.AdoptionApplicationService;
import com.petFounding.repository.AdoptionApplicationRepository;
import com.petFounding.repository.PetRepository;
import com.petFounding.repository.UserRepository;
import com.petFounding.valid.AdoptionApplicationValid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("adoptionApplicationService")
@Transactional
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdoptionApplicationServiceImpl
            (AdoptionApplicationRepository adoptionApplicationRepository,
             PetRepository petRepository,
             UserRepository userRepository) {
        this.adoptionApplicationRepository = adoptionApplicationRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AdoptionApplication crear(AdoptionApplicationValid datos) {
        Pet mascota = petRepository.findById(datos.idMascota())
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada"));

        User adoptante = userRepository.findById(datos.idAdoptante())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (existeSolicitud(adoptante,mascota)) {
            throw new SolicitudDuplicadaException("Ya existe una solicitud de este usuario para esta mascota");
        }

        AdoptionApplication solicitud = new AdoptionApplication();
        solicitud.setMascota(mascota);
        solicitud.setAdoptante(adoptante);
        solicitud.setFechaSolicitud(LocalDate.now());

        return adoptionApplicationRepository.save(solicitud);
    }

    @Override
    public AdoptionApplication aprobar(Long id) {
        AdoptionApplication solicitud = adoptionApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setFechaRta(LocalDate.now());

        Pet mascota = solicitud.getMascota();
        mascota.setEstadoAdopcion(AdoptionStatus.ADOPTADO);
        petRepository.save(mascota);

        return adoptionApplicationRepository.save(solicitud);
    }

    @Override
    public AdoptionApplication rechazar(Long id, String comentario) {
        AdoptionApplication solicitud = adoptionApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setFechaRta(LocalDate.now());
        solicitud.setComentarioRefugio(comentario);

        return adoptionApplicationRepository.save(solicitud);
    }

    @Override
    public void cancelar(Long id) {
        if (!adoptionApplicationRepository.existsById(id)) {
            throw new RuntimeException("Solicitud no encontrada");
        }
        adoptionApplicationRepository.deleteById(id);
    }

    @Override
    public AdoptionApplication obtenerPorId(Long id) {
        return adoptionApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    @Override
    public List<AdoptionApplication> obtenerSolicitudesPorAdoptante(User adoptante) {
        return adoptionApplicationRepository.findByAdoptante(adoptante);
    }

    @Override
    public List<AdoptionApplication> obtenerSolicitudesPorMascota(Pet mascota) {
        return adoptionApplicationRepository.findByMascota(mascota);
    }

    @Override
    public Boolean existeSolicitud(User adoptante, Pet mascota) {
        return adoptionApplicationRepository.existsByAdoptanteAndMascota(adoptante, mascota);
    }
}
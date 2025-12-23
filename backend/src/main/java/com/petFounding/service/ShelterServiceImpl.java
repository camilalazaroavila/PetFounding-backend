package com.petFounding.service;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.interfacee.ShelterService;
import com.petFounding.repository.PetRepository;
import com.petFounding.repository.ShelterRepository;
import com.petFounding.valid.ShelterValid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("shelterService")
@Transactional
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    @Autowired
    public ShelterServiceImpl(ShelterRepository shelterRepository, PetRepository petRepository) {
        this.shelterRepository = shelterRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Shelter crearRefugio(Shelter refugio) {
        return shelterRepository.save(refugio);
    }

    @Override
    public Shelter actualizarRefugio(Long id, ShelterValid refugio) {
        Shelter existente = shelterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado con ID: " + id));

        existente.actualizarDatos(refugio);

        return shelterRepository.save(existente);
    }

    @Override
    public void eliminarRefugio(Long id) {
        if (!shelterRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Refugio no encontrado.");
        }
        shelterRepository.deleteById(id);
    }

    @Override
    public Shelter obtenerRefugioPorId(Long id) {
        return shelterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado."));
    }

    @Override
    public Shelter obtenerRefugioPorNombre(String nombre) {
        return shelterRepository.findByNombreRefugio(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Refugio con nombre " + nombre + " no encontrado."));
    }

    @Override
    public Page<Shelter> obtenerTodosLosRefugios(Pageable paginacion) {
        return shelterRepository.findAll(paginacion);
    }

    @Override
    public List<Pet> gestionarSolicitudes(Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado"));

        return petRepository.findByRefugio(refugio);
    }

    @Override
    public void verReportes(Long idRefugio) {
        shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado"));
    }
}
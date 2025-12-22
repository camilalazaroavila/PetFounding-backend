package com.petFounding.service;


import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.interfacee.ShelterService;
import com.petFounding.repository.PetRepository;
import com.petFounding.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("shelterService")
@Transactional
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    @Autowired
    public ShelterServiceImpl(ShelterRepository shelterRepository,
                              PetRepository petRepository) {
        this.shelterRepository = shelterRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Shelter crearRefugio(Shelter refugio) {
        return shelterRepository.save(refugio);
    }

    @Override
    public Shelter actualizarRefugio(Long id, Shelter refugio) {
        Shelter existente = shelterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        existente.setNombreRefugio(refugio.getNombreRefugio());
        existente.setDireccion(refugio.getDireccion());

        return shelterRepository.save(existente);
    }

    @Override
    public void eliminarRefugio(Long id) {
        shelterRepository.deleteById(id);
    }

    @Override
    public Shelter obtenerRefugioPorId(Long id) {
        return shelterRepository.findById(id).orElse(null);
    }

    @Override
    public Shelter obtenerRefugioPorNombre(String nombre) {
        return shelterRepository.findByNombreRefugio(nombre).orElse(null);
    }

    @Override
    public List<Shelter> obtenerTodosLosRefugios() {
        return shelterRepository.findAll();
    }

    @Override
    public Pet crearMascota(Pet mascota, Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        mascota.setRefugio(refugio);
        return petRepository.save(mascota);
    }

    @Override
    public Pet actualizarMascota(Long idMascota, Pet mascota) {
        Pet existente = petRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        existente.setNombre(mascota.getNombre());
        existente.setRaza(mascota.getRaza());
        existente.setEdad(mascota.getEdad());

        return petRepository.save(existente);
    }

    @Override
    public void eliminarMascota(Long idMascota) {
        petRepository.deleteById(idMascota);
    }

    @Override
    public List<Pet> gestionarSolicitudes(Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        return petRepository.findByRefugio(refugio);
    }

    @Override
    public void verReportes(Long idRefugio) {
        shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));
    }
}
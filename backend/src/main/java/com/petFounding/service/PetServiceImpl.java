package com.petFounding.service;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import com.petFounding.interfacee.PetService;
import com.petFounding.repository.PetRepository;
import com.petFounding.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("petService")
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository,
                          ShelterRepository shelterRepository) {
        this.petRepository = petRepository;
        this.shelterRepository = shelterRepository;
    }

    @Override
    public Pet crearMascota(Pet mascota, Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        mascota.setRefugio(refugio);
        return petRepository.save(mascota);
    }

    @Override
    public Pet registrarMascota(Pet mascota, Long idRefugio) {
        return null;
    }

    @Override
    public Pet actualizarMascota(Long idMascota, Pet mascota) {
        Pet existente = petRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        existente.setNombre(mascota.getNombre());
        existente.setRaza(mascota.getRaza());
        existente.setEdad(mascota.getEdad());
        existente.setSexo(mascota.getSexo());
        existente.setTamano(mascota.getTamano());

        return petRepository.save(existente);
    }

    @Override
    public void eliminarMascota(Long idMascota) {
        petRepository.deleteById(idMascota);
    }

    @Override
    public Pet obtenerDetalle(Long id) {
        return null;
    }

    @Override
    public Pet actualizarEstado(Long id, AdoptionStatus nuevoEstado) {
        return null;
    }

    @Override
    public Pet agregarFoto(Long id, String urlFoto) {
        return null;
    }

    @Override
    public Pet obtenerMascotaPorId(Long idMascota) {
        return petRepository.findById(idMascota).orElse(null);
    }

    @Override
    public List<Pet> obtenerMascotasPorRefugio(Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        return petRepository.findByRefugio(refugio);
    }

    @Override
    public List<Pet> obtenerMascotasPorEstado(AdoptionStatus estado) {
        return List.of();
    }

    @Override
    public List<Pet> obtenerMascotasPorSexo(Sex sexo) {
        return List.of();
    }

    @Override
    public List<Pet> obtenerMascotasPorTamano(Size tamano) {
        return List.of();
    }

    @Override
    public List<Pet> obtenerTodasLasMascotas() {
        return petRepository.findAll();
    }
}

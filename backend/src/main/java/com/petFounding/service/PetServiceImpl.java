package com.petFounding.service;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import com.petFounding.interfaceService.PetService;
import com.petFounding.repository.PetRepository;
import com.petFounding.repository.ShelterRepository;
import com.petFounding.valid.PetValid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("petService")
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, ShelterRepository shelterRepository) {
        this.petRepository = petRepository;
        this.shelterRepository = shelterRepository;
    }

    @Override
    public Pet crearMascota(Pet mascota, Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado con ID: " + idRefugio));

        mascota.setRefugio(refugio);
        return petRepository.save(mascota);
    }

    @Override
    public Pet actualizarMascota(Long idMascota, PetValid mascota) {
        Pet existente = petRepository.findById(idMascota)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada con ID: " + idMascota));

        existente.actualizarDatos(mascota);

        return petRepository.save(existente);
    }

    @Override
    public void eliminarMascota(Long idMascota) {
        if (!petRepository.existsById(idMascota)) {
            throw new EntityNotFoundException("No se puede eliminar: Mascota inexistente.");
        }
        petRepository.deleteById(idMascota);
    }

    @Override
    public Pet obtenerMascotaPorId(Long idMascota) {
        return petRepository.findById(idMascota)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada."));
    }

    @Override
    public Page<Pet> obtenerTodasLasMascotas(Pageable paginacion) {
        return petRepository.findAll(paginacion);
    }

    @Override
    public List<Pet> obtenerMascotasPorRefugio(Long idRefugio) {
        Shelter refugio = shelterRepository.findById(idRefugio)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado"));
        return petRepository.findByRefugio(refugio);
    }

    @Override
    public List<Pet> obtenerMascotasPorEstado(AdoptionStatus estado) {
        return petRepository.findByEstadoAdopcion(estado);
    }

    @Override
    public List<Pet> obtenerMascotasPorSexo(Sex sexo) {
        return petRepository.findBySexo(sexo);
    }

    @Override
    public List<Pet> obtenerMascotasPorTamano(Size tamano) {
        return petRepository.findByTamano(tamano);
    }

    @Override
    public Pet agregarFoto(Long id, String urlFoto){
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada."));
    }
}
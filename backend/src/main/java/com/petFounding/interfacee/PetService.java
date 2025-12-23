package com.petFounding.interfacee;

import com.petFounding.entity.Pet;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import com.petFounding.valid.PetValid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetService {
    Pet crearMascota(Pet mascota, Long idRefugio);
    Pet actualizarMascota(Long id, PetValid mascota);
    void eliminarMascota(Long id);
    Pet agregarFoto(Long id, String urlFoto);
    Page<Pet> obtenerTodasLasMascotas(Pageable paginacion);
    Pet obtenerMascotaPorId(Long idMascota);
    List<Pet> obtenerMascotasPorRefugio(Long idRefugio);
    List<Pet> obtenerMascotasPorEstado(AdoptionStatus estado);
    List<Pet> obtenerMascotasPorSexo(Sex sexo);
    List<Pet> obtenerMascotasPorTamano(Size tamano);
}

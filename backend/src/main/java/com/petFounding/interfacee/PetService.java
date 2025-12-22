package com.petFounding.interfacee;

import com.petFounding.entity.Pet;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;

import java.util.List;

public interface PetService {

    Pet crearMascota(Pet mascota, Long idRefugio);

    Pet registrarMascota(Pet mascota, Long idRefugio);

    Pet actualizarMascota(Long id, Pet mascota);

    void eliminarMascota(Long id);

    Pet obtenerDetalle(Long id);

    Pet actualizarEstado(Long id, AdoptionStatus nuevoEstado);

    Pet agregarFoto(Long id, String urlFoto);

    List<Pet> obtenerTodasLasMascotas();

    Pet obtenerMascotaPorId(Long idMascota);

    List<Pet> obtenerMascotasPorRefugio(Long idRefugio);

    List<Pet> obtenerMascotasPorEstado(AdoptionStatus estado);

    List<Pet> obtenerMascotasPorSexo(Sex sexo);

    List<Pet> obtenerMascotasPorTamano(Size tamano);
}

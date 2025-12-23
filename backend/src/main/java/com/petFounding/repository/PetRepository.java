package com.petFounding.repository;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByRefugio(Shelter refugio);
    List<Pet> findByEstadoAdopcion(AdoptionStatus estado);
    List<Pet> findBySexo(Sex sexo);
    List<Pet> findByTamano(Size tamano);
}

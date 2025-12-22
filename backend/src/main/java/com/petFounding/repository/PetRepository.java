package com.petFounding.repository;

import com.petFounding.entity.Pet;
import com.petFounding.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByRefugio(Shelter refugio);
}

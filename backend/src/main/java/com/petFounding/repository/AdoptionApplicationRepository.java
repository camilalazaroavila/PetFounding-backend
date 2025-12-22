package com.petFounding.repository;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
    List<AdoptionApplication> findByAdoptante(User adoptante);
    List<AdoptionApplication> findByMascota(Pet mascota);
    Boolean existsByAdoptanteAndMascota(User adoptante, Pet mascota);
}
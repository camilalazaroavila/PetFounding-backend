package com.petFounding.repository;

import com.petFounding.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findByNombreRefugio(String nombreRefugio);

    boolean existsByNombreRefugio(String nombreRefugio);
}

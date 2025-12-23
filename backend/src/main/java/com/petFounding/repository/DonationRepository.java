package com.petFounding.repository;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUsuario(User usuario);
    List<Donation> findByRefugio(Shelter refugio);
    List<Donation> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
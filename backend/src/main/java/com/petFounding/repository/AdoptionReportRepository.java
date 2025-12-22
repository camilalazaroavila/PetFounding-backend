package com.petFounding.repository;

import com.petFounding.entity.AdoptionReport;
import com.petFounding.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionReportRepository extends JpaRepository<AdoptionReport, Long> {
    List<AdoptionReport> findByRefugio(Shelter refugio);
    Optional<AdoptionReport> findByPeriodo(LocalDate periodo);
}
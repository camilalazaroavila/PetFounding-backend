package com.petFounding.repository;

import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialReportRepository extends JpaRepository<FinancialReport, Long> {
    List<FinancialReport> findByRefugio(Shelter refugio);
    List<FinancialReport> findByFechaInicio(LocalDate fechaInicio);
}
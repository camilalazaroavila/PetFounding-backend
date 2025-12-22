package com.petFounding.repository;

import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;

import java.time.LocalDate;
import java.util.List;

public interface FinantialReportRepository {
    FinancialReport guardar(FinancialReport reporte);
    FinancialReport modificar(FinancialReport reporte);
    void eliminar(Long id);
    FinancialReport buscarPorId(Long id);
    List<FinancialReport> buscarPorRefugio(Shelter refugio);
    List<FinancialReport> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<FinancialReport> buscarTodos();
}
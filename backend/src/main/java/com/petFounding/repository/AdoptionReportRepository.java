package com.petFounding.repository;

import com.petFounding.entity.AdoptionReport;
import com.petFounding.entity.Shelter;

import java.time.LocalDate;
import java.util.List;

public interface AdoptionReportRepository {
    AdoptionReport guardar(AdoptionReport reporte);
    AdoptionReport modificar(AdoptionReport reporte);
    void eliminar(Long id);
    AdoptionReport buscarPorId(Long id);
    List<AdoptionReport> buscarPorRefugio(Shelter refugio);
    AdoptionReport buscarPorPeriodo(LocalDate periodo);
    List<AdoptionReport> buscarTodos();
}
package com.petFounding.interfacee;

import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;

import java.time.LocalDate;
import java.util.List;

public interface FinantialReportService {
    FinancialReport generar(Shelter refugio, LocalDate fechaInicio, LocalDate fechaFin);

    byte[] exportarPDF(Long id);

    byte[] exportarExcel(Long id);

    FinancialReport obtenerPorId(Long id);

    List<FinancialReport> obtenerReportesPorRefugio(Shelter refugio);
}

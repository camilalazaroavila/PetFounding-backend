package com.petFounding.interfacee;

import com.petFounding.entity.AdoptionReport;
import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;

import java.time.LocalDate;
import java.util.List;

public interface AdoptionReportService {
    FinancialReport generar(Shelter refugio, LocalDate fechaInicio, LocalDate fechaFin);

    AdoptionReport generar(Shelter refugio, LocalDate periodo);

    byte[] exportar(Long id);

    //    byte[] exportarPDF(Long id); this PODRIA SER ASI O USAR UNA LIBRERIA QUE LO GENERE Y CRAR LA ENTIDAD CON ESE NOMBRE
//    byte[] exportarExcel(Long id);
    FinancialReport obtenerPorId(Long id);
    List<FinancialReport> obtenerReportesPorRefugio(Shelter refugio);
}

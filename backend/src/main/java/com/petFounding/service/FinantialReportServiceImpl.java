package com.petFounding.service;


import com.petFounding.entity.Donation;
import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;
import com.petFounding.interfacee.FinantialReportService;
import com.petFounding.repository.DonationRepository;
import com.petFounding.repository.FinantialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service("financialReportService")
@Transactional
public class FinantialReportServiceImpl implements FinantialReportService {

    private FinantialReportRepository financialReportRepository;
    private DonationRepository donationRepository;

    @Autowired
    public FinantialReportServiceImpl(FinantialReportRepository financialReportRepository,
                                      DonationRepository donationRepository) {
        this.financialReportRepository = financialReportRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public FinancialReport generar(Shelter refugio, LocalDate fechaInicio, LocalDate fechaFin) {
        // 1. Obtener todas las donaciones del refugio en el rango de fechas
        List<Donation> donaciones = donationRepository.buscarPorFecha(fechaInicio, fechaFin);

        // 2. Calcular el total recaudado
        BigDecimal totalRecaudado = BigDecimal.ZERO;
        int cantidadDonaciones = 0;

        for (Donation donacion : donaciones) {
            if (donacion.getRefugio().getId().equals(refugio.getId())) {
                totalRecaudado = totalRecaudado.add(donacion.getMonto());
                cantidadDonaciones++;
            }
        }

        // 3. Crear el reporte con los datos calculados
        FinancialReport reporte = new FinancialReport(fechaInicio, fechaFin, totalRecaudado, cantidadDonaciones);
        reporte.setRefugio(refugio);

        // 4. Guardar el reporte en la base de datos
        return financialReportRepository.guardar(reporte);
    }

    @Override
    public byte[] exportarPDF(Long id) {
        // TODO: Implementar exportación a PDF
        // 1. Buscar el reporte por ID
        FinancialReport reporte = financialReportRepository.buscarPorId(id);
        if (reporte == null) {
            throw new RuntimeException("Reporte no encontrado");
        }
        // 2. Generar PDF con los datos del reporte
        // 3. Incluir gráficos y tablas
        // 4. Retornar el PDF como array de bytes
        return null;
    }

    @Override
    public byte[] exportarExcel(Long id) {
        // TODO: Implementar exportación a Excel
        // 1. Buscar el reporte por ID
        FinancialReport reporte = financialReportRepository.buscarPorId(id);
        if (reporte == null) {
            throw new RuntimeException("Reporte no encontrado");
        }
        // 2. Crear archivo Excel con los datos
        // 3. Incluir múltiples hojas si es necesario
        // 4. Retornar el Excel como array de bytes
        return null;
    }

    @Override
    public FinancialReport obtenerPorId(Long id) {
        FinancialReport reporte = financialReportRepository.buscarPorId(id);
        if (reporte == null) {
            throw new RuntimeException("Reporte no encontrado");
        }
        return reporte;
    }

    @Override
    public List<FinancialReport> obtenerReportesPorRefugio(Shelter refugio) {
        return financialReportRepository.buscarPorRefugio(refugio);
    }
}
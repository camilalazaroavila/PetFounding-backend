package com.petFounding.service;

import com.petFounding.entity.Donation;
import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;
import com.petFounding.interfaceService.FinantialReportService;
import com.petFounding.repository.DonationRepository;
import com.petFounding.repository.FinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service("financialReportService")
@Transactional
public class FinantialReportServiceImpl implements FinantialReportService {

    private final FinancialReportRepository financialReportRepository;
    private final DonationRepository donationRepository;

    @Autowired
    public FinantialReportServiceImpl(FinancialReportRepository financialReportRepository,
                                      DonationRepository donationRepository) {
        this.financialReportRepository = financialReportRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public FinancialReport generar(Shelter refugio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Donation> donaciones = donationRepository.findByFechaBetween(fechaInicio, fechaFin);

        BigDecimal totalRecaudado = BigDecimal.ZERO;
        int cantidadDonaciones = 0;

        for (Donation donacion : donaciones) {
            if (donacion.getRefugio().getId().equals(refugio.getId())) {
                totalRecaudado = totalRecaudado.add(donacion.getMonto());
                cantidadDonaciones++;
            }
        }

        FinancialReport reporte = new FinancialReport(fechaInicio, fechaFin, totalRecaudado, cantidadDonaciones);
        reporte.setRefugio(refugio);

        return financialReportRepository.save(reporte);
    }

    @Override
    public byte[] exportarPDF(Long id) {
        FinancialReport reporte = financialReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        // TODO: Implementar lógica de iText o JasperReports
        return null;
    }

    @Override
    public byte[] exportarExcel(Long id) {
        FinancialReport reporte = financialReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        // TODO: Implementar lógica de Apache POI
        return null;
    }

    @Override
    public FinancialReport obtenerPorId(Long id) {
        return financialReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    @Override
    public List<FinancialReport> obtenerReportesPorRefugio(Shelter refugio) {
        return financialReportRepository.findByRefugio(refugio);
    }
}
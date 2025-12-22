package com.petFounding.service;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.AdoptionReport;
import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;
import com.petFounding.interfacee.AdoptionReportService;
import com.petFounding.repository.AdoptionApplicationRepository;
import com.petFounding.repository.AdoptionReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AdoptionReportServiceImpl implements AdoptionReportService {

    private final AdoptionReportRepository adoptionReportRepository;
    private final AdoptionApplicationRepository adoptionApplicationRepository;

    @Autowired
    public AdoptionReportServiceImpl(
            AdoptionReportRepository adoptionReportRepository,
            AdoptionApplicationRepository adoptionApplicationRepository) {
        this.adoptionReportRepository = adoptionReportRepository;
        this.adoptionApplicationRepository = adoptionApplicationRepository;
    }

    @Override
    public FinancialReport generar(Shelter refugio, LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO: instalar libreria o API qq genere PDFS por su cuenta y solo llamarlo en este metodo en el rol de admin
        return null;
    }

    @Override
    public AdoptionReport generar(Shelter refugio, LocalDate periodo) {
        //TODO: lo mismo acá o hacerlo manualemtne
        List<AdoptionApplication> solicitudes =
                adoptionApplicationRepository.buscarTodos();

        int mascotasAdoptadas = 0;
        int solicitudesPendientes = 0;
        int solicitudesAprobadas = 0;
        int solicitudesRechazadas = 0;

        AdoptionReport reporte = new AdoptionReport(
                periodo,
                mascotasAdoptadas,
                solicitudesPendientes,
                solicitudesAprobadas,
                solicitudesRechazadas
        );
        reporte.setRefugio(refugio);

        return adoptionReportRepository.guardar(reporte);
    }

    @Override
    public byte[] exportar(Long id) {
        // TODO: generar PDF con la libreria

        return null;
    }

    @Override
    public FinancialReport obtenerPorId(Long id) {
        // TODO: tnmos  q llamar al repo
        return null;
    }

    @Override
    public List<FinancialReport> obtenerReportesPorRefugio(Shelter refugio) {
        // TODO: tnmos  q llamar al repo
        return null;
    }
}

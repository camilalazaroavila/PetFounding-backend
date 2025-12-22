package com.petFounding.infraestructure;

import com.petFounding.entity.AdoptionReport;
import com.petFounding.entity.Shelter;
import com.petFounding.repository.AdoptionReportRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository("adoptionReportRepository")
public class AdoptionReportRepositoryImpl implements AdoptionReportRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public AdoptionReportRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public AdoptionReport guardar(AdoptionReport reporte) {
        // TODO: guardar reporte
        return null;
    }

    @Override
    public AdoptionReport modificar(AdoptionReport reporte) {
        // TODO:modif report
        return null;
    }

    @Override
    public void eliminar(Long id) {
        // TODO:elim reporte
    }

    @Override
    public AdoptionReport buscarPorId(Long id) {
        // TODO: buscar reporte por id
        return null;
    }

    @Override
    public List<AdoptionReport> buscarPorRefugio(Shelter refugio) {
        // TODO:buscar refgio por Id
        return null;
    }

    @Override
    public AdoptionReport buscarPorPeriodo(LocalDate periodo) {
        // TODO: opcional
        return null;
    }

    @Override
    public List<AdoptionReport> buscarTodos() {
        // TODO:buscar todos los reportes q se muetsre e una lista dsp
        return null;
    }
}
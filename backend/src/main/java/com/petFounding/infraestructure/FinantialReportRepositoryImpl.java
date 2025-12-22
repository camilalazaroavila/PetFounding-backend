package com.petFounding.infraestructure;

import com.petFounding.entity.FinancialReport;
import com.petFounding.entity.Shelter;
import com.petFounding.repository.FinantialReportRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository("financialReportRepository")
public class FinantialReportRepositoryImpl implements FinantialReportRepository{

    private SessionFactory sessionFactory;

    @Autowired
    public FinantialReportRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public FinancialReport guardar(FinancialReport reporte) {
        // TODO: guardar reporte
        return null;
    }

    @Override
    public FinancialReport modificar(FinancialReport reporte) {
        // TODO: modificar reoprte
        return null;
    }

    @Override
    public void eliminar(Long id) {
        // TODO: eliminar reporte
    }

    @Override
    public FinancialReport buscarPorId(Long id) {
        // TODO: x id
        return null;
    }

    @Override
    public List<FinancialReport> buscarPorRefugio(Shelter refugio) {
        // TODO: Busquda por refuigio
        return null;
    }

    @Override
    public List<FinancialReport> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO: opcional por rango de fechas

        return null;
    }

    @Override
    public List<FinancialReport> buscarTodos() {
        // TODO: busqueda d todos los reportes

        return null;
    }
}
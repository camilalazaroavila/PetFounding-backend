package com.petFounding.infraestructure;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import com.petFounding.repository.DonationRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository("donationRepository")
public class DonationRepositoryImpl implements DonationRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public DonationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Donation guardar(Donation donacion) {
        // TODO: save
        return null;
    }

    @Override
    public Donation modificar(Donation donacion) {
        // TODO: setamos
        return null;
    }

    @Override
    public void eliminar(Long id) {
        // TODO: llamamos al metodo de buscar por id y dsp hacemos un .delete
    }

    @Override
    public Donation buscarPorId(Long id) {
        // TODO: 
        return null;
    }

    @Override
    public List<Donation> buscarPorUsuario(User usuario) {
        // TODO: buscar por id
        return null;
    }

    @Override
    public List<Donation> buscarPorRefugio(Shelter refugio) {
        // TODO: busar por id
        return null;
    }

    @Override
    public List<Donation> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO: opcional
        return null;
    }

    @Override
    public List<Donation> buscarTodos() {
        // TODO: buscar donaciones
        return null;
    }
}
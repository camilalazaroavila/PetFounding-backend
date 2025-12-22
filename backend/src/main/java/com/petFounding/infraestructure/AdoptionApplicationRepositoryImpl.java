package com.petFounding.infraestructure;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;
import com.petFounding.repository.AdoptionApplicationRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("adoptionApplicationRepository")
public class AdoptionApplicationRepositoryImpl implements AdoptionApplicationRepository {
// no se si conviene hacelo entidad  o directamente un form a claro el form guarda los datos aca
    private SessionFactory sessionFactory;

    @Autowired
    public AdoptionApplicationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public AdoptionApplication guardar(AdoptionApplication solicitud) {
        // TODO: guadar soliciud
        return null;
    }

    @Override
    public AdoptionApplication modificar(AdoptionApplication solicitud) {
        // TODO: modificar soictud /no se si hacerlo
        return null;
    }

    @Override
    public void eliminar(Long id) {
        // TODO: ekliminar
    }

    @Override
    public AdoptionApplication buscarPorId(Long id) {
        // TODO:busqueda por id en mascota o usuario?????
        return null;
    }

    @Override
    public List<AdoptionApplication> buscarPorAdoptante(User adoptante) {
        // TODO:busqueda por adoptante llama el metodo d arriba
        return null;
    }

    @Override
    public List<AdoptionApplication> buscarPorMascota(Pet mascota) {
        // TODO: buscar por id llamams al meotod de arriaba
        return null;
    }

    @Override
    public Boolean existePorAdoptanteYMascota(User adoptante, Pet mascota) {
        // TODO: cheuqear q existan ambps
        return null;
    }

    @Override
    public List<AdoptionApplication> buscarTodos() {
        // TODO: buscar solicitudes
        return null;
    }
}
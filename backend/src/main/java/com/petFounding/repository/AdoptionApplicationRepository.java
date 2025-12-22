package com.petFounding.repository;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;

import java.util.List;

public interface AdoptionApplicationRepository {
    AdoptionApplication guardar(AdoptionApplication solicitud);
    AdoptionApplication modificar(AdoptionApplication solicitud);
    void eliminar(Long id);
    AdoptionApplication buscarPorId(Long id);
    List<AdoptionApplication> buscarPorAdoptante(User adoptante);
    List<AdoptionApplication> buscarPorMascota(Pet mascota);
    Boolean existePorAdoptanteYMascota(User adoptante, Pet mascota);
    List<AdoptionApplication> buscarTodos();
}
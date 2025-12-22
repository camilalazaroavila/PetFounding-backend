//package com.petFounding.infraestructure;
//
//import com.petFounding.entity.Pet;
//import com.petFounding.entity.Shelter;
//import com.petFounding.enumerator.AdoptionStatus;
//import com.petFounding.enumerator.Sex;
//import com.petFounding.enumerator.Size;
//import com.petFounding.repository.PetRepository;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository("petRepository")
//public class PetRepositoryImpl implements PetRepository {
//
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public PetRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public Pet guardar(Pet mascota) {
//        return null;
//    }
//
//    @Override
//    public Pet modificar(Pet mascota) {
//       //update
//        return null;
//    }
//
//    @Override
//    public void eliminar(Long id) {
//
//    }
//
//    @Override
//    public Pet buscarPorId(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarPorRefugio(Shelter refugio) {
//
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarPorEstado(AdoptionStatus estado) {
//
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarPorSexo(Sex sexo) {
//
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarPorTamano(Size tamano) {
//
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarPorRaza(String raza) {
//
//        return null;
//    }
//
//    @Override
//    public List<Pet> buscarTodos() {
//
//        return null;
//    }
//}
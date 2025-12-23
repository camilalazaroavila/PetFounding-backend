package com.petFounding.controller;

import com.petFounding.entity.Pet;
import com.petFounding.repository.PetRepository;
import com.petFounding.valid.PetValid;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @GetMapping
    public ResponseEntity<Page<Pet>> listaPets(@PageableDefault(size=6) Pageable pag){
        return ResponseEntity.ok(repository.findAll(pag));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Pet> agregarPet(@RequestBody @Valid PetValid datos, UriComponentsBuilder uriBuilder){
        Pet pet = new Pet(datos);

        Pet petSave = repository.save(pet);

        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(petSave.getId()).toUri();
        return ResponseEntity.created(uri).body(petSave);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> detallesPet(@PathVariable Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada"));
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody @Valid PetValid datos) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada para actualizar"));
        pet.actualizarDatos(datos);
        repository.save(pet);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Pet> deletePet(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}

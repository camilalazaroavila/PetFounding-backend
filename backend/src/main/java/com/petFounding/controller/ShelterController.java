package com.petFounding.controller;

import com.petFounding.entity.Shelter;
import com.petFounding.repository.ShelterRepository;
import com.petFounding.valid.ShelterValid;
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
@RequestMapping("/shelter")
public class ShelterController {

    @Autowired
    private ShelterRepository repository;

    @GetMapping
    public ResponseEntity<Page<Shelter>> listShelters(@PageableDefault(size=6) Pageable pag){
        return ResponseEntity.ok(repository.findAll(pag));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Shelter> addShelter(@RequestBody @Valid ShelterValid datos, UriComponentsBuilder uriBuilder) {
        Shelter shelter = repository.save(new Shelter(datos));
        URI uri = uriBuilder.path("/shelters/{id}").buildAndExpand(shelter.getId()).toUri();
        return ResponseEntity.created(uri).body(shelter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shelter> getShelter(@PathVariable Long id) {
        Shelter shelter = repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Refugio no encontrado."));
        return ResponseEntity.ok(shelter);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Shelter> updateShelter(@PathVariable Long id, @RequestBody @Valid ShelterValid datos) {
        Shelter shelter = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Refugio no encontrado para actualizar"));
        shelter.actualizarDatos(datos);
        repository.save(shelter);
        return ResponseEntity.ok(shelter);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Shelter> deleteShelter(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}

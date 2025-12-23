package com.petFounding.controller;

import com.petFounding.entity.Pet;
import com.petFounding.interfacee.PetService;
import com.petFounding.valid.PetValid;
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
    private PetService petService;

    @GetMapping
    public ResponseEntity<Page<Pet>> listaPets(@PageableDefault(size=6) Pageable pag){
        return ResponseEntity.ok(petService.obtenerTodasLasMascotas(pag));
    }

    @PostMapping
    public ResponseEntity<Pet> agregarPet(@RequestBody @Valid PetValid datos, UriComponentsBuilder uriBuilder){
        Pet pet = new Pet(datos);

        Pet petSave = petService.crearMascota(pet, pet.getRefugio().getId());

        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(petSave.getId()).toUri();
        return ResponseEntity.created(uri).body(petSave);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> detallesPet(@PathVariable Long id) {
        Pet pet = petService.obtenerMascotaPorId(id);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody @Valid PetValid datos) {
        Pet petActualizada = petService.actualizarMascota(id, datos);
        return ResponseEntity.ok(petActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id){
        petService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}
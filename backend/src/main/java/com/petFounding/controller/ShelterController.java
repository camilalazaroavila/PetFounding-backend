package com.petFounding.controller;

import com.petFounding.entity.Shelter;
import com.petFounding.interfacee.ShelterService;
import com.petFounding.valid.ShelterValid;
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
    private ShelterService shelterService;

    @GetMapping
    public ResponseEntity<Page<Shelter>> listShelters(@PageableDefault(size = 6, sort = "nombreRefugio") Pageable pag) {
        return ResponseEntity.ok(shelterService.obtenerTodosLosRefugios(pag));
    }

    @PostMapping
    public ResponseEntity<Shelter> addShelter(@RequestBody @Valid ShelterValid datos, UriComponentsBuilder uriBuilder) {
        Shelter shelter = new Shelter(datos);
        Shelter shelterGuardado = shelterService.crearRefugio(shelter);

        URI uri = uriBuilder.path("/shelter/{id}").buildAndExpand(shelterGuardado.getId()).toUri();
        return ResponseEntity.created(uri).body(shelterGuardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shelter> getShelter(@PathVariable Long id) {
        Shelter shelter = shelterService.obtenerRefugioPorId(id);
        return ResponseEntity.ok(shelter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shelter> updateShelter(@PathVariable Long id, @RequestBody @Valid ShelterValid datos) {
        Shelter shelterActualizado = shelterService.actualizarRefugio(id, datos);
        return ResponseEntity.ok(shelterActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.eliminarRefugio(id);
        return ResponseEntity.noContent().build();
    }
}
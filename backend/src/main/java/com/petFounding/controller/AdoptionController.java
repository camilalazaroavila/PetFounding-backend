package com.petFounding.controller;

import com.petFounding.entity.AdoptionApplication;
import com.petFounding.entity.Pet;
import com.petFounding.entity.User;
import com.petFounding.interfaceService.AdoptionApplicationService;
import com.petFounding.valid.AdoptionApplicationValid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionApplicationService adoptionApplicationService;

    // 1. Crear una nueva solicitud de adopción
    @PostMapping
    public ResponseEntity<AdoptionApplication> crearSolicitud(@RequestBody @Valid AdoptionApplicationValid datos, UriComponentsBuilder uriBuilder) {
        AdoptionApplication nuevaSolicitud = adoptionApplicationService.crear(datos);
        URI uri = uriBuilder.path("/adoptions/{id}").buildAndExpand(nuevaSolicitud.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevaSolicitud);
    }

    // 2. Obtener una solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<AdoptionApplication> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionApplicationService.obtenerPorId(id));
    }

    // 3. Aprobar una solicitud (Cambia estado de mascota a ADOPTADO)
    @PutMapping("/{id}/approve")
    public ResponseEntity<AdoptionApplication> aprobarSolicitud(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionApplicationService.aprobar(id));
    }

    // 4. Rechazar una solicitud con comentario
    @PutMapping("/{id}/reject")
    public ResponseEntity<AdoptionApplication> rechazarSolicitud(
            @PathVariable Long id,
            @RequestBody String comentario) {
        return ResponseEntity.ok(adoptionApplicationService.rechazar(id, comentario));
    }

    // 5. Cancelar/Eliminar una solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarSolicitud(@PathVariable Long id) {
        adoptionApplicationService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Listar solicitudes por Adoptante (Usuario)
    @GetMapping("/user")
    public ResponseEntity<List<AdoptionApplication>> obtenerPorAdoptante(@RequestParam Long id) {
        User user = new User();
        user.setId(id);
        return ResponseEntity.ok(adoptionApplicationService.obtenerSolicitudesPorAdoptante(user));
    }

    // 7. Listar solicitudes por Mascota
    @GetMapping("/pet")
    public ResponseEntity<List<AdoptionApplication>> obtenerPorMascota(@RequestBody Pet mascota) {
        return ResponseEntity.ok(adoptionApplicationService.obtenerSolicitudesPorMascota(mascota));
    }
}
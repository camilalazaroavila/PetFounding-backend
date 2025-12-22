package com.petFounding.controller;

import com.petFounding.entity.Shelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private com.petFounding.interfacee.ShelterService shelterService;

    @GetMapping("/")
    public String home() {
        return "\n" +
                "PET FOUNDING BACKEND Y CONECTADO A BD \n" +
                " Estado: FUNCIONANDO\n" +
                " Puerto: 3000\n";
    }

    @GetMapping("/api/test")

    public String test() {
        return "Backend funcionando ";
    }

    @GetMapping("/api/test/db")
    public ResponseEntity<Map<String, Object>> testDatabase() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Shelter> shelters = shelterService.obtenerTodosLosRefugios();

            response.put("status", "SUCCESS");
            response.put("message", "Conexión a BD exitosa");
            response.put("cantidadRefugios", shelters.size());
            response.put("refugios", shelters);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error al conectar con BD");
            response.put("error", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

//endpoint para probar bd
@PostMapping("/api/test/shelter")
    public ResponseEntity<Map<String, Object>> createTestShelter() {
        Map<String, Object> response = new HashMap<>();

        try {
            Shelter shelter = new Shelter();
            shelter.setNombreRefugio("Refugio Test " + System.currentTimeMillis());
            shelter.setDireccion("Calle Falsa 123");


            Shelter saved = shelterService.crearRefugio(shelter);

            response.put("status", "SUCCESS");
            response.put("message", "Refugio creado exitosamente");
            response.put("refugio", saved);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error al crear refugio");
            response.put("error", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
package com.petFounding.controller;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import com.petFounding.interfaceService.DonationService;
import com.petFounding.interfaceService.ShelterService;
import com.petFounding.interfaceService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/donations")
@CrossOrigin(origins = "${cors.allowed.origins}")
public class DonationController {

    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShelterService shelterService;


    @GetMapping
    public ResponseEntity<?> obtenerTodasLasDonaciones() {
        try {
            List<Donation> donaciones = donationService.obtenerTodasLasDonaciones();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("data", donaciones);
            response.put("total", donaciones.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error(" Error al obtener donaciones", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al obtener donaciones",
                        "error", e.getMessage()
                    ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDonacionPorId(@PathVariable Long id) {
        try {
            Donation donacion = donationService.obtenerDonacionPorId(id);

            if (donacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "NOT_FOUND",
                            "message", "Donación no encontrada con ID: " + id
                        ));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("data", donacion);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error(" Error al obtener donación con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al obtener donación",
                        "error", e.getMessage()
                    ));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> obtenerDonacionesPorUsuario(@PathVariable Long userId) {
        try {
            List<Donation> donaciones = donationService.obtenerDonacionesPorUsuario(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("data", donaciones);
            response.put("userId", userId);
            response.put("total", donaciones.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error al obtener donaciones del usuario: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al obtener donaciones del usuario",
                        "error", e.getMessage()
                    ));
        }
    }


    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<?> obtenerDonacionesPorRefugio(@PathVariable Long shelterId) {
        try {
            List<Donation> donaciones = donationService.obtenerDonacionesPorRefugio(shelterId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("data", donaciones);
            response.put("shelterId", shelterId);
            response.put("total", donaciones.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error al obtener donaciones del refugio: {}", shelterId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al obtener donaciones del refugio",
                        "error", e.getMessage()
                    ));
        }
    }


    @PostMapping
    public ResponseEntity<?> crearDonacion(@RequestBody Map<String, Object> request) {
        try {

            Long userId = Long.valueOf(request.get("userId").toString());
            Long shelterId = Long.valueOf(request.get("shelterId").toString());
            Double monto = Double.valueOf(request.get("monto").toString());
            String metodoDePago = (String) request.getOrDefault("metodoDePago", "EFECTIVO");
            String mensaje = (String) request.getOrDefault("mensaje", "");

            logger.info("📝 Creando donación directa - Usuario: {}, Refugio: {}, Monto: {}",
                    userId, shelterId, monto);

            if (monto <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "status", "ERROR",
                            "message", "El monto debe ser mayor a 0"
                        ));
            }


            User usuario = userService.obtenerUsuarioPorId(userId);
            if (usuario == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "status", "ERROR",
                            "message", "Usuario no encontrado"
                        ));
            }

            Shelter refugio = shelterService.obtenerRefugioPorId(shelterId);
            if (refugio == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "status", "ERROR",
                            "message", "Refugio no encontrado"
                        ));
            }


            Donation donacion = new Donation();
            donacion.setMonto(monto);
            donacion.setFecha(LocalDateTime.now());
            donacion.setMetodoDePago(metodoDePago);
            donacion.setMensaje(mensaje);
            donacion.setUsuario(usuario);
            donacion.setRefugio(refugio);


            Donation donacionGuardada = donationService.processar(donacion);

            logger.info(" Donación creada exitosamente - ID: {}", donacionGuardada.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Donación creada exitosamente");
            response.put("data", donacionGuardada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("❌ Error al crear donación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al crear donación",
                        "error", e.getMessage()
                    ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDonacion(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        try {
            Donation donacionExistente = donationService.obtenerDonacionPorId(id);

            if (donacionExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "NOT_FOUND",
                            "message", "Donación no encontrada con ID: " + id
                        ));
            }


            if (request.containsKey("monto")) {
                donacionExistente.setMonto(Double.valueOf(request.get("monto").toString()));
            }
            if (request.containsKey("metodoDePago")) {
                donacionExistente.setMetodoDePago((String) request.get("metodoDePago"));
            }
            if (request.containsKey("mensaje")) {
                donacionExistente.setMensaje((String) request.get("mensaje"));
            }

            Donation donacionActualizada = donationService.processar(donacionExistente);

            logger.info("✅ Donación actualizada - ID: {}", id);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Donación actualizada exitosamente");
            response.put("data", donacionActualizada);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error al actualizar donación con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al actualizar donación",
                        "error", e.getMessage()
                    ));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDonacion(@PathVariable Long id) {
        try {
            Donation donacion = donationService.obtenerDonacionPorId(id);

            if (donacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "NOT_FOUND",
                            "message", "Donación no encontrada con ID: " + id
                        ));
            }

            donationService.eliminarDonacion(id);

            logger.info("✅ Donación eliminada - ID: {}", id);

            return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "message", "Donación eliminada exitosamente"
            ));

        } catch (Exception e) {
            logger.error("❌ Error al eliminar donación con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al eliminar donación",
                        "error", e.getMessage()
                    ));
        }
    }


    @GetMapping("/stats/shelter/{shelterId}")
    public ResponseEntity<?> obtenerEstadisticasRefugio(@PathVariable Long shelterId) {
        try {
            List<Donation> donaciones = donationService.obtenerDonacionesPorRefugio(shelterId);

            // Calcular estadísticas
            double totalDonado = donaciones.stream()
                    .mapToDouble(Donation::getMonto)
                    .sum();

            int cantidadDonaciones = donaciones.size();

            double promedio = cantidadDonaciones > 0 ? totalDonado / cantidadDonaciones : 0;

            Map<String, Object> stats = new HashMap<>();
            stats.put("shelterId", shelterId);
            stats.put("totalDonado", totalDonado);
            stats.put("cantidadDonaciones", cantidadDonaciones);
            stats.put("promedioPorDonacion", promedio);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("data", stats);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error al obtener estadísticas del refugio: {}", shelterId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "Error al obtener estadísticas",
                        "error", e.getMessage()
                    ));
        }
    }
}
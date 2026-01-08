//package com.petFounding.controller;
//
//import com.petFounding.entity.AdoptionApplication;
//import com.petFounding.entity.Pet;
//import com.petFounding.entity.User;
//import com.petFounding.enumerator.AdoptionStatus;
//import com.petFounding.interfaceService.AdoptionApplicationService;
//import com.petFounding.interfaceService.PetService;
//import com.petFounding.interfaceService.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * ✅ AdoptionController
// *
// * Gestiona todas las operaciones de adopción
// *
// * Endpoints:
// * - GET    /adoptions                    -> Obtener todas las solicitudes
// * - GET    /adoptions/{id}               -> Obtener solicitud por ID
// * - GET    /adoptions/user/{userId}      -> Solicitudes de un usuario
// * - GET    /adoptions/pet/{petId}        -> Solicitudes para una mascota
// * - GET    /adoptions/status/{status}    -> Solicitudes por estado
// * - POST   /adoptions                    -> Crear nueva solicitud
// * - PUT    /adoptions/{id}               -> Actualizar solicitud
// * - PUT    /adoptions/{id}/status        -> Cambiar estado de solicitud
// * - DELETE /adoptions/{id}               -> Eliminar solicitud
// */
//@RestController
//@RequestMapping("/adoptions")
//@CrossOrigin(origins = "${cors.allowed.origins}")
//public class AdoptionController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AdoptionController.class);
//
//    @Autowired
//    private AdoptionApplicationService adoptionService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PetService petService;
//
//    /**
//     * GET /api/v1/adoptions
//     * Obtener todas las solicitudes de adopción
//     */
//    @GetMapping
//    public ResponseEntity<?> obtenerTodasLasSolicitudes() {
//        try {
//            List<AdoptionApplication> solicitudes = adoptionService.obtenerTodasLasSolicitudes();
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", solicitudes);
//            response.put("total", solicitudes.size());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener solicitudes de adopción", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener solicitudes",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * GET /api/v1/adoptions/{id}
//     * Obtener solicitud por ID
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<?> obtenerSolicitudPorId(@PathVariable Long id) {
//        try {
//            AdoptionApplication solicitud = adoptionService.obtenerSolicitudPorId(id);
//
//            if (solicitud == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of(
//                                "status", "NOT_FOUND",
//                                "message", "Solicitud no encontrada con ID: " + id
//                        ));
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", solicitud);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener solicitud con ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener solicitud",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * GET /api/v1/adoptions/user/{userId}
//     * Obtener solicitudes de un usuario
//     */
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> obtenerSolicitudesPorUsuario(@PathVariable Long userId) {
//        try {
//            List<AdoptionApplication> solicitudes = adoptionService.obtenerSolicitudesPorUsuario(userId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", solicitudes);
//            response.put("userId", userId);
//            response.put("total", solicitudes.size());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener solicitudes del usuario: {}", userId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener solicitudes del usuario",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * GET /api/v1/adoptions/pet/{petId}
//     * Obtener solicitudes para una mascota
//     */
//    @GetMapping("/pet/{petId}")
//    public ResponseEntity<?> obtenerSolicitudesPorMascota(@PathVariable Long petId) {
//        try {
//            List<AdoptionApplication> solicitudes = adoptionService.obtenerSolicitudesPorMascota(petId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", solicitudes);
//            response.put("petId", petId);
//            response.put("total", solicitudes.size());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener solicitudes para la mascota: {}", petId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener solicitudes de la mascota",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * GET /api/v1/adoptions/status/{status}
//     * Obtener solicitudes por estado
//     *
//     * Estados válidos: PENDING, APPROVED, REJECTED, CANCELLED
//     */
//    @GetMapping("/status/{status}")
//    public ResponseEntity<?> obtenerSolicitudesPorEstado(@PathVariable String status) {
//        try {
//            AdoptionStatus adoptionStatus;
//            try {
//                adoptionStatus = AdoptionStatus.valueOf(status.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Estado inválido. Estados válidos: PENDING, APPROVED, REJECTED, CANCELLED"
//                        ));
//            }
//
//            List<AdoptionApplication> solicitudes = adoptionService.obtenerSolicitudesPorEstado(adoptionStatus);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", solicitudes);
//            response.put("adoptionStatus", adoptionStatus);
//            response.put("total", solicitudes.size());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener solicitudes con estado: {}", status, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener solicitudes por estado",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * POST /api/v1/adoptions
//     * Crear nueva solicitud de adopción
//     *
//     * Body JSON:
//     * {
//     *   "userId": 1,
//     *   "petId": 1,
//     *   "reason": "Quiero adoptar porque...",
//     *   "livingSpace": "Casa con jardín",
//     *   "hasOtherPets": true,
//     *   "experience": "He tenido mascotas toda mi vida"
//     * }
//     */
//    @PostMapping
//    public ResponseEntity<?> crearSolicitud(@RequestBody Map<String, Object> request) {
//        try {
//            // Extraer y validar datos
//            Long userId = Long.valueOf(request.get("userId").toString());
//            Long petId = Long.valueOf(request.get("petId").toString());
//            String reason = (String) request.get("reason");
//            String livingSpace = (String) request.getOrDefault("livingSpace", "");
//            Boolean hasOtherPets = (Boolean) request.getOrDefault("hasOtherPets", false);
//            String experience = (String) request.getOrDefault("experience", "");
//
//            logger.info("📝 Creando solicitud de adopción - Usuario: {}, Mascota: {}",
//                    userId, petId);
//
//            // Validaciones
//            if (reason == null || reason.trim().isEmpty()) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Debe proporcionar una razón para la adopción"
//                        ));
//            }
//
//            // Verificar que existan el usuario y la mascota
//            User usuario = userService.obtenerUsuarioPorId(userId);
//            if (usuario == null) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Usuario no encontrado"
//                        ));
//            }
//
//            Pet mascota = petService.obtenerMascotaPorId(petId);
//            if (mascota == null) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Mascota no encontrada"
//                        ));
//            }
//
//            // Verificar que la mascota esté disponible
//            if (!mascota.getDisponible()) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "La mascota no está disponible para adopción"
//                        ));
//            }
//
//            // Crear la solicitud
//            AdoptionApplication solicitud = new AdoptionApplication();
//            solicitud.setUsuario(usuario);
//            solicitud.setMascota(mascota);
//            solicitud.setRazon(reason);
//            solicitud.setEspacioVivir(livingSpace);
//            solicitud.setTieneOtrasMascotas(hasOtherPets);
//            solicitud.setExperiencia(experience);
//            solicitud.setFechaSolicitud(LocalDate.now());
//            solicitud.setEstado(AdoptionStatus.PENDING);
//
//            // Guardar
//            AdoptionApplication solicitudGuardada = adoptionService.crearSolicitud(solicitud);
//
//            logger.info("✅ Solicitud de adopción creada - ID: {}", solicitudGuardada.getId());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("message", "Solicitud de adopción creada exitosamente");
//            response.put("data", solicitudGuardada);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al crear solicitud de adopción", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al crear solicitud",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * PUT /api/v1/adoptions/{id}
//     * Actualizar información de una solicitud
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<?> actualizarSolicitud(
//            @PathVariable Long id,
//            @RequestBody Map<String, Object> request) {
//
//        try {
//            AdoptionApplication solicitudExistente = adoptionService.obtenerSolicitudPorId(id);
//
//            if (solicitudExistente == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of(
//                                "status", "NOT_FOUND",
//                                "message", "Solicitud no encontrada con ID: " + id
//                        ));
//            }
//
//            // Actualizar campos si están presentes
//            if (request.containsKey("reason")) {
//                solicitudExistente.setRazon((String) request.get("reason"));
//            }
//            if (request.containsKey("livingSpace")) {
//                solicitudExistente.setEspacioVivir((String) request.get("livingSpace"));
//            }
//            if (request.containsKey("hasOtherPets")) {
//                solicitudExistente.setTieneOtrasMascotas((Boolean) request.get("hasOtherPets"));
//            }
//            if (request.containsKey("experience")) {
//                solicitudExistente.setExperiencia((String) request.get("experience"));
//            }
//
//            AdoptionApplication solicitudActualizada = adoptionService.actualizarSolicitud(solicitudExistente);
//
//            logger.info("✅ Solicitud actualizada - ID: {}", id);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("message", "Solicitud actualizada exitosamente");
//            response.put("data", solicitudActualizada);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al actualizar solicitud con ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al actualizar solicitud",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * PUT /api/v1/adoptions/{id}/status
//     * Cambiar el estado de una solicitud
//     *
//     * Body JSON:
//     * {
//     *   "status": "APPROVED"  // o "REJECTED", "CANCELLED"
//     * }
//     */
//    @PutMapping("/{id}/status")
//    public ResponseEntity<?> cambiarEstadoSolicitud(
//            @PathVariable Long id,
//            @RequestBody Map<String, String> request) {
//
//        try {
//            AdoptionApplication solicitud = adoptionService.obtenerSolicitudPorId(id);
//
//            if (solicitud == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of(
//                                "status", "NOT_FOUND",
//                                "message", "Solicitud no encontrada con ID: " + id
//                        ));
//            }
//
//            String nuevoEstadoStr = request.get("status");
//            if (nuevoEstadoStr == null) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Debe proporcionar un estado"
//                        ));
//            }
//
//            AdoptionStatus nuevoEstado;
//            try {
//                nuevoEstado = AdoptionStatus.valueOf(nuevoEstadoStr.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of(
//                                "status", "ERROR",
//                                "message", "Estado inválido. Estados válidos: PENDING, APPROVED, REJECTED, CANCELLED"
//                        ));
//            }
//
//            // Actualizar estado
//            solicitud.setEstado(nuevoEstado);
//
//            // Si se aprueba, marcar la mascota como no disponible
//            if (nuevoEstado == AdoptionStatus.APPROVED) {
//                Pet mascota = solicitud.getMascota();
//                mascota.setDisponible(false);
//                petService.actualizarMascota(mascota);
//
//                logger.info("✅ Mascota {} marcada como adoptada", mascota.getId());
//            }
//
//            AdoptionApplication solicitudActualizada = adoptionService.actualizarSolicitud(solicitud);
//
//            logger.info("✅ Estado de solicitud actualizado - ID: {}, Nuevo estado: {}",
//                    id, nuevoEstado);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("message", "Estado actualizado exitosamente");
//            response.put("data", solicitudActualizada);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al cambiar estado de solicitud con ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al cambiar estado",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * DELETE /api/v1/adoptions/{id}
//     * Eliminar una solicitud de adopción
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> eliminarSolicitud(@PathVariable Long id) {
//        try {
//            AdoptionApplication solicitud = adoptionService.obtenerSolicitudPorId(id);
//
//            if (solicitud == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of(
//                                "status", "NOT_FOUND",
//                                "message", "Solicitud no encontrada con ID: " + id
//                        ));
//            }
//
//            adoptionService.eliminarSolicitud(id);
//
//            logger.info("✅ Solicitud eliminada - ID: {}", id);
//
//            return ResponseEntity.ok(Map.of(
//                    "status", "SUCCESS",
//                    "message", "Solicitud eliminada exitosamente"
//            ));
//
//        } catch (Exception e) {
//            logger.error("❌ Error al eliminar solicitud con ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al eliminar solicitud",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//
//    /**
//     * GET /api/v1/adoptions/stats/user/{userId}
//     * Obtener estadísticas de adopción de un usuario
//     */
//    @GetMapping("/stats/user/{userId}")
//    public ResponseEntity<?> obtenerEstadisticasUsuario(@PathVariable Long userId) {
//        try {
//            List<AdoptionApplication> solicitudes = adoptionService.obtenerSolicitudesPorUsuario(userId);
//
//            long pendientes = solicitudes.stream()
//                    .filter(s -> s.getEstado() == AdoptionStatus.PENDING)
//                    .count();
//
//            long aprobadas = solicitudes.stream()
//                    .filter(s -> s.getEstado() == AdoptionStatus.APPROVED)
//                    .count();
//
//            long rechazadas = solicitudes.stream()
//                    .filter(s -> s.getEstado() == AdoptionStatus.REJECTED)
//                    .count();
//
//            Map<String, Object> stats = new HashMap<>();
//            stats.put("userId", userId);
//            stats.put("totalSolicitudes", solicitudes.size());
//            stats.put("pendientes", pendientes);
//            stats.put("aprobadas", aprobadas);
//            stats.put("rechazadas", rechazadas);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", "SUCCESS");
//            response.put("data", stats);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("❌ Error al obtener estadísticas del usuario: {}", userId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "status", "ERROR",
//                            "message", "Error al obtener estadísticas",
//                            "error", e.getMessage()
//                    ));
//        }
//    }
//}
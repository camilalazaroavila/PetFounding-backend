package com.petFounding.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.petFounding.entity.User;
import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.interfaceService.UserService;
import com.petFounding.interfaceService.DonationService;
import com.petFounding.interfaceService.ShelterService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "${cors.allowed.origins}")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShelterService shelterService;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${app.base.url}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
        logger.info(" MercadoPago configurado correctamente");
        logger.info(" Base URL para callbacks: {}", baseUrl);
    }


    @PostMapping("/payments/crear-donacion")
    public ResponseEntity<?> crearPagoDonacion(
            @RequestBody Map<String, Object> request,
            HttpSession session) {

        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long shelterId = Long.valueOf(request.get("shelterId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String donorName = (String) request.get("donorName");
            String donorEmail = (String) request.get("donorEmail");
            String message = (String) request.getOrDefault("message", "");

            logger.info("📝 Creando donación - Usuario: {}, Refugio: {}, Monto: ARS {}",
                    userId, shelterId, amount);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El monto debe ser mayor a 0"));
            }

            User usuario = userService.obtenerUsuarioPorId(userId);
            if (usuario == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Usuario no encontrado"));
            }

            Shelter refugio = shelterService.obtenerRefugioPorId(shelterId);
            if (refugio == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Refugio no encontrado"));
            }

            String sessionKey = "donation_" + System.currentTimeMillis();
            session.setAttribute(sessionKey + "_userId", userId);
            session.setAttribute(sessionKey + "_shelterId", shelterId);
            session.setAttribute(sessionKey + "_amount", amount);
            session.setAttribute(sessionKey + "_donorName", donorName);
            session.setAttribute(sessionKey + "_donorEmail", donorEmail);
            session.setAttribute(sessionKey + "_message", message);

            String titulo = "Donación para " + refugio.getNombreRefugio();
            String descripcion = "Donación de " + donorName + " para ayudar a las mascotas";

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(titulo)
                    .description(descripcion)
                    .quantity(1)
                    .unitPrice(amount)
                    .currencyId("ARS")
                    .build();

            String frontendUrl = "http://localhost:5173";

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(frontendUrl + "/donation/success?session=" + sessionKey)
                    .failure(frontendUrl + "/donation/failure?session=" + sessionKey)
                    .pending(frontendUrl + "/donation/pending?session=" + sessionKey)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .statementDescriptor("DONACION REFUGIO")
                    .externalReference(sessionKey)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            logger.info(" Preferencia creada exitosamente - ID: {}", preference.getId());

            Map<String, String> response = new HashMap<>();
            response.put("initPoint", preference.getInitPoint());
            response.put("preferenceId", preference.getId());
            response.put("sessionKey", sessionKey);

            return ResponseEntity.ok(response);

        } catch (MPApiException e) {
            logger.error(" Error de API de MercadoPago - Status: {}, Body: {}",
                    e.getApiResponse().getStatusCode(),
                    e.getApiResponse().getContent());
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error con Mercado Pago: " + e.getMessage()));

        } catch (MPException e) {
            logger.error(" Error de SDK de MercadoPago", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error procesando el pago: " + e.getMessage()));

        } catch (Exception e) {
            logger.error(" Error general al crear preferencia", e);
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error inesperado: " + e.getMessage()));
        }
    }

    @GetMapping("/payments/procesar-exitoso")
    public ResponseEntity<?> procesarPagoExitoso(
            @RequestParam String sessionKey,
            @RequestParam(required = false) String payment_id,
            @RequestParam(required = false) String status,
            HttpSession session) {

        logger.info(" Procesando pago exitoso - Payment ID: {}, Session: {}", payment_id, sessionKey);

        try {

            Long userId = (Long) session.getAttribute(sessionKey + "_userId");
            Long shelterId = (Long) session.getAttribute(sessionKey + "_shelterId");
            BigDecimal amount = (BigDecimal) session.getAttribute(sessionKey + "_amount");
            String donorName = (String) session.getAttribute(sessionKey + "_donorName");
            String donorEmail = (String) session.getAttribute(sessionKey + "_donorEmail");
            String message = (String) session.getAttribute(sessionKey + "_message");

            if (userId == null || shelterId == null || amount == null) {
                logger.warn(" Datos de sesión incompletos para sessionKey: {}", sessionKey);
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "error", "Sesión expirada o datos incompletos",
                                "status", "WARNING"
                        ));
            }

            User usuario = userService.obtenerUsuarioPorId(userId);
            Shelter refugio = shelterService.obtenerRefugioPorId(shelterId);


            Donation donacion = new Donation();
            donacion.setMonto(amount);
            donacion.setFecha(LocalDate.from(LocalDateTime.now()));
            donacion.setMetodoDePago("MERCADO_PAGO");
            donacion.setMensaje(message != null ? message : "Donación vía Mercado Pago");
            donacion.setUsuario(usuario);
            donacion.setRefugio(refugio);


            Donation donacionGuardada = donationService.processar(donacion);

            logger.info(" Donación guardada exitosamente - ID: {}", donacionGuardada.getId());

            limpiarSesion(session, sessionKey);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "¡Gracias por tu donación! 🎉");
            response.put("donationId", donacionGuardada.getId());
            response.put("amount", amount);
            response.put("shelter", refugio.getNombreRefugio());
            response.put("donor", donorName);
            response.put("paymentId", payment_id);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error(" Error al procesar donación después del pago", e);
            return ResponseEntity.status(500)
                    .body(Map.of(
                            "error", "Error al guardar la donación: " + e.getMessage(),
                            "status", "ERROR"
                    ));
        }
    }


    @GetMapping("/payments/procesar-pendiente")
    public ResponseEntity<?> procesarPagoPendiente(
            @RequestParam String sessionKey,
            @RequestParam(required = false) String payment_id) {

        logger.info("⏳ Pago pendiente - Payment ID: {}, Session: {}", payment_id, sessionKey);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "PENDING");
        response.put("message", "Tu pago está siendo procesado ⏳");
        response.put("description", "Te notificaremos cuando se confirme el pago.");
        response.put("paymentId", payment_id);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/payments/procesar-fallido")
    public ResponseEntity<?> procesarPagoFallido(
            @RequestParam String sessionKey,
            @RequestParam(required = false) String payment_id,
            HttpSession session) {

        logger.error(" Pago fallido - Payment ID: {}, Session: {}", payment_id, sessionKey);


        limpiarSesion(session, sessionKey);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "FAILURE");
        response.put("message", "No se pudo procesar tu pago ");
        response.put("description", "Por favor, verifica tus datos e intenta nuevamente");
        response.put("paymentId", payment_id);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/payments/webhook")
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> payload) {
        logger.info("📩 Webhook recibido de Mercado Pago: {}", payload);

        try {
            String type = (String) payload.get("type");

            if ("payment".equals(type)) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String paymentId = (String) data.get("id");

                logger.info("💳 Notificación de pago recibida - Payment ID: {}", paymentId);


            }

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            logger.error(" Error procesando webhook", e);
            return ResponseEntity.status(500).body("ERROR");
        }
    }

    private void limpiarSesion(HttpSession session, String sessionKey) {
        session.removeAttribute(sessionKey + "_userId");
        session.removeAttribute(sessionKey + "_shelterId");
        session.removeAttribute(sessionKey + "_amount");
        session.removeAttribute(sessionKey + "_donorName");
        session.removeAttribute(sessionKey + "_donorEmail");
        session.removeAttribute(sessionKey + "_message");
    }
}
package com.petFounding.infraestructure.errors;

import com.petFounding.infraestructure.exception.SolicitudDuplicadaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorManager {

    // 404 - Cuando no se encuentra un recurso
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(EntityNotFoundException e) {
        String msg = (e.getMessage() == null) ? "Recurso no encontrado" : e.getMessage();
        return ResponseEntity.status(404).body(new ErrorMensaje(msg));
    }

    // 400 - Error de validación de campos (Bean Validation @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    // 400 - Error de lógica de negocio (Solicitud ya existe)
    @ExceptionHandler(SolicitudDuplicadaException.class)
    public ResponseEntity errorSolicitudDuplicada(SolicitudDuplicadaException e) {
        return ResponseEntity.badRequest().body(new ErrorMensaje(e.getMessage()));
    }

    // 400 - Otros errores de validación manual
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorDeValidacion(ValidationException e) {
        return ResponseEntity.badRequest().body(new ErrorMensaje(e.getMessage()));
    }

    // Records para formatear las respuestas
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ErrorMensaje(String mensaje) { }
}
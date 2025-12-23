package com.petFounding.infraestructure.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorManager {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(new ErrorMensaje(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorDeValidacion(ValidationException e) {
        return ResponseEntity.badRequest().body(new ErrorMensaje(e.getMessage()));
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ErrorMensaje(String mensaje) { }
}
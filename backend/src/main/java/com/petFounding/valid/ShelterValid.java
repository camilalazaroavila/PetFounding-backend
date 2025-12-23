package com.petFounding.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShelterValid(
        @NotBlank(message = "El nombre del refugio es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombreRefugio,

        @NotBlank(message = "La dirección es obligatoria")
        String direccion
) {
}
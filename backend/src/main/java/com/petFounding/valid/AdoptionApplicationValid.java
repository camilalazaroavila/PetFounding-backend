package com.petFounding.valid;

import jakarta.validation.constraints.NotNull;

public record AdoptionApplicationValid(
        @NotNull(message = "El ID de la mascota es obligatorio")
        Long idMascota,

        @NotNull(message = "El ID del adoptante es obligatorio")
        Long idAdoptante
) {
}
package com.petFounding.valid;

import java.time.LocalDate;

import com.petFounding.entity.Shelter;
import com.petFounding.enumerator.AdoptionStatus;
import com.petFounding.enumerator.Sex;
import com.petFounding.enumerator.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PetValid(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La raza es obligatoria")
        String raza,

        @NotNull(message = "La edad es obligatoria")
        Integer edad,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "La fecha de ingreso es obligatoria")
        @PastOrPresent
        LocalDate fechaIngreso,

        @NotNull(message = "El sexo es obligatorio")
        Sex sexo,

        @NotNull(message = "El tamaño es obligatorio")
        Size tamano,

        @NotNull(message = "El estado de adopción es obligatorio")
        AdoptionStatus estadoAdopcion,

        @NotNull
        Boolean esterilizado,

        @NotNull
        Boolean vacunado,

        @NotNull(message = "El refugio debe estar asignado")
        Shelter refugio
) {
}
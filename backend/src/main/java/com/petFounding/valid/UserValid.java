package com.petFounding.valid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserValid(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 256) String password,
        @NotBlank String nombre,
        @NotBlank String apellido,
        String telefono,
        String direccion
) {}
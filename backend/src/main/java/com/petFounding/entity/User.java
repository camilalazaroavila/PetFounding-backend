package com.petFounding.entity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
    private Boolean activo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Donation> donaciones = new ArrayList<>();

    public User(String email, String password, String nombre, String apellido, String telefono, String direccion) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = LocalDate.now();
        this.activo = true;
    }
}
package com.petFounding.controller;

import com.petFounding.entity.User;
import com.petFounding.infraestructure.exception.MailIncorrectoException;
import com.petFounding.infraestructure.exception.MailRepetidoException;
import com.petFounding.infraestructure.exception.UsuarioInexistenteException;
import com.petFounding.interfaceService.UserService;
import com.petFounding.valid.UserValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registrar(@RequestBody @Valid UserValid datos, UriComponentsBuilder uriBuilder) throws MailRepetidoException {
        User usuario = new User(datos.email(), datos.password(), datos.nombre(),
                datos.apellido(), datos.telefono(), datos.direccion());

        User usuarioGuardado = userService.registrar(usuario);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(usuarioGuardado.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioGuardado);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest login) throws MailIncorrectoException {
        User usuario = userService.login(login.email(), login.password());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerPerfil(@PathVariable Long id) throws UsuarioInexistenteException {
        return ResponseEntity.ok(userService.obtenerUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> actualizar(@PathVariable Long id, @RequestBody User usuario) throws UsuarioInexistenteException {
        return ResponseEntity.ok(userService.actualizarPerfil(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) throws UsuarioInexistenteException {
        userService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        return ResponseEntity.ok(userService.obtenerTodosLosUsuarios());
    }

    private record LoginRequest(@NotBlank String email, @NotBlank String password) {}
}
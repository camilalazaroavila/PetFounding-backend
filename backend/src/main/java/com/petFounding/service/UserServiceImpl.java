package com.petFounding.service;

import com.petFounding.entity.User;
import com.petFounding.exception.MailIncorrectoException;
import com.petFounding.exception.MailRepetidoException;
import com.petFounding.exception.UsuarioInexistenteException;
import com.petFounding.interfacee.UserService;
import com.petFounding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registrar(User usuario) throws MailRepetidoException {
        if (userRepository.existsByEmail(usuario.getEmail())) {
            throw new MailRepetidoException("El email ya existe");
        }

        usuario.setFechaRegistro(LocalDate.now());
        usuario.setActivo(true);
        return userRepository.save(usuario);
    }

    @Override
    public User login(String email, String password) throws MailIncorrectoException {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new MailIncorrectoException("Credenciales incorrectas"));
    }

    @Override
    public User actualizarPerfil(Long id, User usuario) throws UsuarioInexistenteException {
        User usuarioExistente = userRepository.findById(id)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setDireccion(usuario.getDireccion());

        return userRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarCuenta(Long id) throws UsuarioInexistenteException {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario no encontrado"));

        usuario.setActivo(false);
        userRepository.save(usuario);
    }

    @Override
    public User obtenerUsuarioPorId(Long id) throws UsuarioInexistenteException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario no encontrado"));
    }

    @Override
    public User obtenerUsuarioPorEmail(String email) throws UsuarioInexistenteException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuario con email " + email + " no encontrado"));
    }

    @Override
    public List<User> obtenerTodosLosUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public Boolean existeEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
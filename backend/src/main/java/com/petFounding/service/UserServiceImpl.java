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

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//TODO:AGREGAR TODAS LAS EXCEPCIONES EN la carpeta d expciones y cambiar el RunTimeExcepctionn por el nombre de la expecion, como el primer metdo
    @Override
    public User registrar(User usuario) throws MailRepetidoException {
        if (userRepository.existePorEmail(usuario.getEmail())) {
            throw new MailRepetidoException("El email ya existe");
        }

        usuario.setFechaRegistro(LocalDate.now());
        usuario.setActivo(true);
        return userRepository.guardar(usuario);
    }

    @Override
    public User login(String email, String password) throws MailIncorrectoException {
        User usuario = userRepository.buscarPorEmailYPassword(email, password);

        if (usuario == null) {
            throw new MailIncorrectoException("Credenciales incorrectas");
        }

        return usuario;
    }

    @Override
    public User actualizarPerfil(Long id, User usuario) throws UsuarioInexistenteException {
        User usuarioExistente = userRepository.buscarPorId(id);

        if (usuarioExistente == null) {
            throw new UsuarioInexistenteException("Usuario no encontrado");
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setDireccion(usuario.getDireccion());

        return userRepository.modificar(usuarioExistente);
    }

    @Override
    public void eliminarCuenta(Long id) throws UsuarioInexistenteException {
        User usuario = userRepository.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioInexistenteException("Usuario no encontrado");
        }

        usuario.setActivo(false);
        userRepository.modificar(usuario);
    }

    @Override
    public User obtenerUsuarioPorId(Long id) throws UsuarioInexistenteException {
        User usuario = userRepository.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioInexistenteException("Usuario no encontrado");
        }

        return usuario;
    }

    @Override
    public User obtenerUsuarioPorEmail(String email) throws UsuarioInexistenteException {
        User usuario = userRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioInexistenteException("Usuario no encontrado");
        }

        return usuario;
    }

    @Override
    public List<User> obtenerTodosLosUsuarios() {
        return userRepository.buscarTodos();
    }

    @Override
    public Boolean existeEmail(String email) {
        return userRepository.existePorEmail(email);
    }
}
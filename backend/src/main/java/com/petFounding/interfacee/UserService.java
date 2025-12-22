package com.petFounding.interfacee;


import com.petFounding.entity.User;
import com.petFounding.exception.MailIncorrectoException;
import com.petFounding.exception.MailRepetidoException;
import com.petFounding.exception.UsuarioInexistenteException;

import java.util.List;

public interface UserService {
        User registrar(User usuario) throws MailRepetidoException;
        User login(String email, String password) throws MailIncorrectoException;
        User actualizarPerfil(Long id, User usuario) throws UsuarioInexistenteException;
        void eliminarCuenta(Long id) throws UsuarioInexistenteException;
        User obtenerUsuarioPorId(Long id) throws UsuarioInexistenteException;
        User obtenerUsuarioPorEmail(String email) throws UsuarioInexistenteException;
        List<User> obtenerTodosLosUsuarios();
        Boolean existeEmail(String email);
    }
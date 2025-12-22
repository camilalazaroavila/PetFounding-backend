package com.petFounding.repository;

import com.petFounding.entity.User;

import java.util.List;

public interface UserRepository {
    User guardar(User usuario);
    User modificar(User usuario);
    void eliminar(Long id);
    User buscarPorId(Long id);
    User buscarPorEmail(String email);
    User buscarPorEmailYPassword(String email, String password);
    List<User> buscarTodos();
    Boolean existePorEmail(String email);
}
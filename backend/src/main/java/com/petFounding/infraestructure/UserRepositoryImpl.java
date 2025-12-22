package com.petFounding.infraestructure;

import com.petFounding.entity.User;
import com.petFounding.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User guardar(User usuario) {
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;  // ✅ CORRECTO
    }

    @Override
    public User modificar(User usuario) {
        sessionFactory.getCurrentSession().update(usuario);
        return usuario;  // ✅ CORRECTO
    }

    @Override
    public void eliminar(Long id) {
        User usuario = buscarPorId(id);
        sessionFactory.getCurrentSession().delete(usuario);
    }

    @Override
    public User buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User buscarPorEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        return (User) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public User buscarPorEmailYPassword(String email, String password) {
        String hql = "FROM User WHERE email = :email AND password = :password";
        return (User) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
    }

    @Override
    public List<User> buscarTodos() {
        String hql = "FROM User";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .list();
    }

    @Override
    public Boolean existePorEmail(String email) {
        String hql = "SELECT COUNT(*) FROM User WHERE email = :email";
        Long count = (Long) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("email", email)
                .uniqueResult();
        return count > 0;
    }
}
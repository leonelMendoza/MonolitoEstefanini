package org.example.monolito.dao;

import org.example.monolito.model.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    Usuario save(Usuario usuario) throws SQLException;
    Optional<Usuario> findById(Long id) throws SQLException;
    List<Usuario> findAll() throws SQLException;
    boolean deleteById(Long id) throws SQLException;
}

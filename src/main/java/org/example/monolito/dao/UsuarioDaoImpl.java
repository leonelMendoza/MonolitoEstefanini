package org.example.monolito.dao;

import org.example.monolito.model.Usuario;
import org.example.monolito.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsuarioDaoImpl implements UsuarioDao{

    private long sequence = 1L;
    @Override
    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO users(sequence++, name,email,status) VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = DBConnection.get().prepareStatement(sql);
            ps.setLong(1, usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id){
        String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement ps = null;
        Usuario usuario  = null;
        try {
            ps = DBConnection.get().prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario =  Usuario.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("name"))
                        .correo(rs.getString("email"))
                        .status(rs.getString("status"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(usuario);
    }


    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DBConnection.get()
                    .prepareStatement("SELECT * FROM users")
                    .executeQuery();
            while (rs.next()) {
                usuarios.add( Usuario.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("name"))
                        .correo(rs.getString("email"))
                        .status(rs.getString("status"))
                        .build());
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public boolean deleteById(Long id) {
        PreparedStatement ps = null;
        try {
            ps = DBConnection.get()
                    .prepareStatement("DELETE FROM users WHERE id=?");
            ps.setLong(1, id);
            return ps.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

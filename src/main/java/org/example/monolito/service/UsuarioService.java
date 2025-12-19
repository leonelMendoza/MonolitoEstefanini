package org.example.monolito.service;

import org.example.monolito.dto.UsuarioRequestDTO;
import org.example.monolito.dto.UsuarioResponseDTO;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request) throws SQLException;
    UsuarioResponseDTO getById(Long id);
    List<UsuarioResponseDTO> getAll();
    boolean borrarUsuario(Long id);
}

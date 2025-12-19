package org.example.monolito.service;

import org.example.monolito.dao.UsuarioDao;
import org.example.monolito.dao.UsuarioDaoImpl;
import org.example.monolito.dto.UsuarioRequestDTO;
import org.example.monolito.dto.UsuarioResponseDTO;
import org.example.monolito.model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioDao dao = new UsuarioDaoImpl();
    private Optional<Usuario> optionalUsuario = null;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .build();
        try {
            return mapearDto(dao.save(usuario));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public UsuarioResponseDTO getById(Long id) {
        try {
            optionalUsuario = dao.findById(id);
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
                return mapearDto(usuario);
            } else {
                throw new RuntimeException("Usuario no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> usuarios = null;
        try {
            usuarios = dao.findAll();
            List<UsuarioResponseDTO> result = new ArrayList<>();

            for (Usuario user : usuarios) {
                result.add(mapearDto(user));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean borrarUsuario(Long id) {
        try {
            return dao.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private UsuarioResponseDTO mapearDto(Usuario user) {
        return UsuarioResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .status(user.getStatus())
                .build();
    }
}

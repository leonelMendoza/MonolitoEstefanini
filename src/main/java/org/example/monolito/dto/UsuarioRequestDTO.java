package org.example.monolito.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UsuarioRequestDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String status;
}

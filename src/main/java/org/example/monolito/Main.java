package org.example.monolito;

import org.example.monolito.config.SimuladorUsuarios;
import org.example.monolito.dto.UsuarioRequestDTO;
import org.example.monolito.dto.UsuarioResponseDTO;
import org.example.monolito.socket.SocketServer;

public class Main {
    public static void main(String[] args) {
        String[] op = {""};
        try {
           SocketServer.main(op);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
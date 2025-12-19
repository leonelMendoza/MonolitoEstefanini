package org.example.monolito.socket;

import org.example.monolito.config.SimuladorUsuarios;
import org.example.monolito.dto.UsuarioRequestDTO;
import org.example.monolito.dto.UsuarioResponseDTO;
import org.example.monolito.model.Usuario;
import org.example.monolito.service.UsuarioService;
import org.example.monolito.service.UsuarioServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private static final UsuarioService service = new UsuarioServiceImpl();
    private static long sequence = 1L;
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9090);
        System.out.println("Socket server running on port 9090");

        while (true) {
            Socket client = server.accept();
            new Thread(() -> handle(client)).start();
        }
    }

    private static void handle(Socket client) {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            out.println("OK;Connected");
            out.println("INFO;Server ready");

            String line;

            while ((line = in.readLine()) != null) {

                String[] parts = line.split(";");

                if ("EXIT".equalsIgnoreCase(parts[0])) {
                    out.println("OK;Bye");
                    break;
                }

                if ("CREATE_USER".equals(parts[0])) {

                    service.crear(mapearDto(
                            Usuario.builder()
                                    .id(sequence++)
                                    .nombre(parts[1])
                                    .correo(parts[2])
                                    .status(parts[3])
                                    .build()));

                    out.println("OK;Usuario creado");
                }

                else if ("GET_USER".equals(parts[0])) {

                    UsuarioResponseDTO usuario =
                            service.getById(Long.parseLong(parts[1]));

                    if (usuario != null)
                        out.println("OK;" + usuario.getNombre());
                    else
                        out.println("ERROR;Usuario no encontrado");
                }

                else if ("GET_ALLUSER".equals(parts[0])) {

                    List<UsuarioResponseDTO> usuarios = service.getAll();

                    if (usuarios != null) {
                        for (UsuarioResponseDTO u : usuarios) {
                            out.println("OK;" + u.getNombre());
                        }
                    } else {
                        out.println("ERROR;Usuario no encontrado");
                    }
                }

                else if ("BORRAR_USER".equals(parts[0])) {

                    if (service.borrarUsuario(Long.parseLong(parts[1])))
                        out.println("OK;Usuario eliminado");
                    else
                        out.println("ERROR;Usuario no eliminado");
                }
                else if ("SIMULAR_USER".equals(parts[0])) {
                    simularUsuarios();
                }
                else {
                    out.println("ERROR;Comando desconocido");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    private static UsuarioRequestDTO mapearDto(Usuario usuario) {
        return UsuarioRequestDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .status(usuario.getStatus())
                .build();
    }

    private static void simularUsuarios() {


            //SocketServer.main(op);
            System.out.println("Iniciando simulación concurrente:");
            UsuarioRequestDTO Usr1 = UsuarioRequestDTO.builder()
                    .id(1L)
                    .nombre("Leo")
                    .correo("leo@mail.com")
                    .status("ACTIVO")
                    .build();


            UsuarioRequestDTO Usr2 = UsuarioRequestDTO.builder()
                    .id(2L)
                    .nombre("Pedro")
                    .correo("pedro@mail.com")
                    .status("ACTIVO")
                    .build();


            System.out.println("Simulación iniciada.....");
            SimuladorUsuarios.procesarUsuarios(Usr1);
            SimuladorUsuarios.procesarUsuarios(Usr2);

          System.out.println("Simulación finalizada.");

    }



}

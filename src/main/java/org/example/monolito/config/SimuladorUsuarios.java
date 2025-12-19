package org.example.monolito.config;

import org.example.monolito.dto.UsuarioRequestDTO;
import org.example.monolito.dto.UsuarioResponseDTO;
import org.example.monolito.model.Usuario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimuladorUsuarios {

    private static final ExecutorService EXECUTOR =
            Executors.newFixedThreadPool(5);

    public static void procesarUsuarios(UsuarioRequestDTO usuario) {

        EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                String thread = Thread.currentThread().getName();
                System.out.println("Inicio usuario " + usuario.getId() + " en " + thread);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Fin usuario " + usuario.getId() + " en " + thread);
            }
        });
    }

}

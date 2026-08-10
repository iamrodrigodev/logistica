package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.exception;

import java.time.LocalDateTime;

public record ReadErrorResponse(
        int status,
        String error,
        String mensaje,
        String path,
        LocalDateTime timestamp
) {
}
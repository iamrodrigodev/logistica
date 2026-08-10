package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.exception;

import java.time.LocalDateTime;

public record WriteErrorResponse(

        int status,
        String error,
        String mensaje,
        String path,
        LocalDateTime timestamp

) {
}
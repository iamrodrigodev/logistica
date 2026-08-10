package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarioporid;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultarUsuarioPorIdResponse(
        Long usuarioId,
        Long personaId,
        String nombre,
        String correo,
        String origen,
        String tipoCuenta,
        String estadoCuenta,
        LocalDate fechaInicioVigencia,
        LocalDate fechaFinVigencia,
        boolean estadoRegistro,
        LocalDateTime fechaCreacion,
        Long actorCreacionId,
        LocalDateTime fechaModificacion,
        Long actorModificacionId
) {
}
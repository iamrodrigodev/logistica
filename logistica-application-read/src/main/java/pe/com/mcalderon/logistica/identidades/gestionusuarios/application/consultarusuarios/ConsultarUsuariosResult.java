package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.EstadoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultarUsuariosResult(

        Long usuarioId,
        Long personaId,
        String nombre,
        String correo,
        OrigenUsuario origen,
        TipoCuenta tipoCuenta,
        EstadoCuenta estadoCuenta,
        LocalDate fechaInicioVigencia,
        LocalDate fechaFinVigencia,
        boolean estadoRegistro,
        LocalDateTime fechaCreacion,
        Long actorCreacionId,
        LocalDateTime fechaModificacion,
        Long actorModificacionId

) {
}
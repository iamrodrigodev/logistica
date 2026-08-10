package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario;

import java.time.LocalDate;

public record ModificarUsuarioCommand(
        Long usuarioId,
        String nombre,
        String correo,
        char origen,
        char tipoCuenta,
        LocalDate fechaInicioVigencia,
        Long actorModificacionId
) {
}
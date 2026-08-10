package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

import java.time.LocalDate;

public record CrearUsuarioCommand(

        Long personaId,
        String nombre,
        String correo,
        char origen,
        char tipoCuenta,
        LocalDate fechaInicioVigencia,
        Long actorCreacionId

) {
}
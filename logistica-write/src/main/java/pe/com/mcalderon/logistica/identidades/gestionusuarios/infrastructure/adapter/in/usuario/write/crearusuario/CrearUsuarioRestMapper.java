package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario;


import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioResult;

import java.util.Objects;

public final class CrearUsuarioRestMapper {

    public CrearUsuarioCommand aCommand(
            CrearUsuarioRequest request
    ) {
        Objects.requireNonNull(
                request,
                "CrearUsuarioRequest no puede ser nulo"
        );

        return new CrearUsuarioCommand(
                request.personaId(),
                request.nombre(),
                request.correo(),
                request.origen(),
                request.tipoCuenta(),
                request.fechaInicioVigencia(),
                request.actorCreacionId()
        );
    }

    public CrearUsuarioResponse aResponse(
            CrearUsuarioResult result
    ) {
        Objects.requireNonNull(
                result,
                "CrearUsuarioResult no puede ser nulo"
        );

        return new CrearUsuarioResponse(
                result.usuarioId()
        );
    }
}
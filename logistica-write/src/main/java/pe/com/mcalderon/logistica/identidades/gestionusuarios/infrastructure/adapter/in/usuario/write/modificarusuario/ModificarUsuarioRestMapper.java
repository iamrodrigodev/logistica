package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.modificarusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioCommand;

import java.util.Objects;

public final class ModificarUsuarioRestMapper {

    public ModificarUsuarioCommand aCommand(
            Long usuarioId,
            ModificarUsuarioRequest request
    ) {
        Objects.requireNonNull(
                usuarioId,
                "UsuarioId no puede ser nulo"
        );

        Objects.requireNonNull(
                request,
                "ModificarUsuarioRequest no puede ser nulo"
        );

        return new ModificarUsuarioCommand(
                usuarioId,
                request.nombre(),
                request.correo(),
                request.origen(),
                request.tipoCuenta(),
                request.fechaInicioVigencia(),
                request.actorModificacionId()
        );
    }
}
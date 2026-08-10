package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.dardebajausuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioCommand;

public final class DarDeBajaUsuarioRestMapper {

    public DarDeBajaUsuarioCommand aCommand(
            Long usuarioId,
            DarDeBajaUsuarioRequest request
    ) {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario no puede ser nulo"
            );
        }

        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud para dar de baja el usuario no puede ser nula"
            );
        }

        return new DarDeBajaUsuarioCommand(
                usuarioId,
                request.actorModificacionId()
        );
    }
}
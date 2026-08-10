package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.eliminarlogicamenteusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioCommand;

public final class EliminarLogicamenteUsuarioRestMapper {

    public EliminarLogicamenteUsuarioCommand aCommand(
            Long usuarioId,
            EliminarLogicamenteUsuarioRequest request
    ) {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario no puede ser nulo"
            );
        }

        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud para eliminar lógicamente el usuario no puede ser nula"
            );
        }

        return new EliminarLogicamenteUsuarioCommand(
                usuarioId,
                request.actorModificacionId()
        );
    }
}
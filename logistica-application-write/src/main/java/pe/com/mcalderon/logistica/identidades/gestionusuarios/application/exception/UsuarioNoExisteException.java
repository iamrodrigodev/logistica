package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

public final class UsuarioNoExisteException extends RuntimeException {

    public UsuarioNoExisteException(UsuarioId usuarioId) {
        super(
                "No existe un usuario registrado con el identificador: "
                        + usuarioId.valor()
        );
    }
}

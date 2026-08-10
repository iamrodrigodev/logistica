package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.util.Objects;

public class EliminarFisicamenteUsuarioHandler
        implements EliminarFisicamenteUsuarioUseCase {

    private final CargarUsuarioPorIdPort cargarUsuarioPorIdPort;
    private final EliminarFisicamenteUsuarioPort eliminarFisicamenteUsuarioPort;

    public EliminarFisicamenteUsuarioHandler(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            EliminarFisicamenteUsuarioPort eliminarFisicamenteUsuarioPort
    ) {
        this.cargarUsuarioPorIdPort = Objects.requireNonNull(
                cargarUsuarioPorIdPort,
                "CargarUsuarioPorIdPort no puede ser nulo"
        );

        this.eliminarFisicamenteUsuarioPort = Objects.requireNonNull(
                eliminarFisicamenteUsuarioPort,
                "EliminarFisicamenteUsuarioPort no puede ser nulo"
        );
    }

    @Override
    public void eliminarFisicamenteUsuario(
            EliminarFisicamenteUsuarioCommand command
    ) {
        if (command == null) {
            throw new IllegalArgumentException(
                    "El comando para eliminar físicamente el usuario no puede ser nulo"
            );
        }

        UsuarioId usuarioId =
                new UsuarioId(
                        command.usuarioId()
                );

        cargarUsuarioPorIdPort
                .cargarPorId(usuarioId)
                .orElseThrow(
                        () -> new UsuarioNoExisteException(
                                usuarioId
                        )
                );

        eliminarFisicamenteUsuarioPort.eliminarFisicamente(
                usuarioId
        );
    }
}
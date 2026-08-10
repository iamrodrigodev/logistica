package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public class EliminarLogicamenteUsuarioHandler
        implements EliminarLogicamenteUsuarioUseCase {

    private final CargarUsuarioPorIdPort cargarUsuarioPorIdPort;
    private final GuardarUsuarioPort guardarUsuarioPort;
    private final Clock clock;

    public EliminarLogicamenteUsuarioHandler(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            GuardarUsuarioPort guardarUsuarioPort,
            Clock clock
    ) {
        this.cargarUsuarioPorIdPort = Objects.requireNonNull(
                cargarUsuarioPorIdPort,
                "CargarUsuarioPorIdPort no puede ser nulo"
        );

        this.guardarUsuarioPort = Objects.requireNonNull(
                guardarUsuarioPort,
                "GuardarUsuarioPort no puede ser nulo"
        );

        this.clock = Objects.requireNonNull(
                clock,
                "Clock no puede ser nulo"
        );
    }

    @Override
    public void eliminarLogicamenteUsuario(
            EliminarLogicamenteUsuarioCommand command
    ) {
        if (command == null) {
            throw new IllegalArgumentException(
                    "El comando para eliminar lógicamente el usuario no puede ser nulo"
            );
        }

        UsuarioId usuarioId =
                new UsuarioId(
                        command.usuarioId()
                );

        ActorAuditoriaId actorModificacionId =
                new ActorAuditoriaId(
                        command.actorModificacionId()
                );

        Usuario usuario = cargarUsuarioPorIdPort
                .cargarPorId(usuarioId)
                .orElseThrow(
                        () -> new UsuarioNoExisteException(
                                usuarioId
                        )
                );

        LocalDateTime fechaModificacion =
                LocalDateTime.now(clock);

        usuario.eliminarLogicamente(
                fechaModificacion,
                actorModificacionId
        );

        guardarUsuarioPort.guardar(usuario);
    }
}
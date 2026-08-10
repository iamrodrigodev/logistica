package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.util.Objects;

public class ConsultarUsuarioPorIdHandler
        implements ConsultarUsuarioPorIdUseCase {

    private final ConsultarUsuarioPorIdPort consultarUsuarioPorIdPort;

    public ConsultarUsuarioPorIdHandler(
            ConsultarUsuarioPorIdPort consultarUsuarioPorIdPort
    ) {
        this.consultarUsuarioPorIdPort = Objects.requireNonNull(
                consultarUsuarioPorIdPort,
                "El puerto para consultar usuarios no puede ser nulo"
        );
    }

    @Override
    public ConsultarUsuarioPorIdResult handle(
            ConsultarUsuarioPorIdQuery query
    ) {
        Objects.requireNonNull(
                query,
                "La consulta de usuario no puede ser nula"
        );

        UsuarioId usuarioId = new UsuarioId(query.usuarioId());

        return consultarUsuarioPorIdPort
                .consultarPorId(usuarioId)
                .orElseThrow(
                        () -> new UsuarioNoExisteException(usuarioId)
                );
    }
}
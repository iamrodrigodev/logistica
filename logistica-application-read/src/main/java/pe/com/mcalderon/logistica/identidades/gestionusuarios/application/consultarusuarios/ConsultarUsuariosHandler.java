package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

import java.util.Objects;

public class ConsultarUsuariosHandler
        implements ConsultarUsuariosUseCase {

    private final ConsultarUsuariosPort consultarUsuariosPort;

    public ConsultarUsuariosHandler(
            ConsultarUsuariosPort consultarUsuariosPort
    ) {
        this.consultarUsuariosPort = Objects.requireNonNull(
                consultarUsuariosPort,
                "ConsultarUsuariosPort no puede ser nulo"
        );
    }

    @Override
    public ConsultarUsuariosPageResult consultar(
            ConsultarUsuariosQuery query
    ) {
        Objects.requireNonNull(
                query,
                "ConsultarUsuariosQuery no puede ser nulo"
        );

        return consultarUsuariosPort.consultar(query);
    }
}
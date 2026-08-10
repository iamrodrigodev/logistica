package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarios;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosQuery;

import java.util.Objects;

public final class ConsultarUsuariosRestMapper {

    public ConsultarUsuariosQuery aQuery(
            ConsultarUsuariosRequest request
    ) {
        Objects.requireNonNull(
                request,
                "ConsultarUsuariosRequest no puede ser nulo"
        );

        return new ConsultarUsuariosQuery(
                request.nombre(),
                request.correo(),
                request.pagina(),
                request.tamanio()
        );
    }
}
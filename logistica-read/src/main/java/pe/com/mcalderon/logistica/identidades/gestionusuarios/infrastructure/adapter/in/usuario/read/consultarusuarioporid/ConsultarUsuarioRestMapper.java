package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarioporid;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdResult;

import java.util.Objects;

public final class ConsultarUsuarioRestMapper {

    public ConsultarUsuarioPorIdResponse aResponse(
            ConsultarUsuarioPorIdResult  result
    ) {
        Objects.requireNonNull(
                result,
                "ConsultarUsuarioPorIdResult  no puede ser nulo"
        );

        return new ConsultarUsuarioPorIdResponse(
                result.usuarioId(),
                result.personaId(),
                result.nombre(),
                result.correo(),
                result.origen().name(),
                result.tipoCuenta().name(),
                result.estadoCuenta().name(),
                result.fechaInicioVigencia(),
                result.fechaFinVigencia(),
                result.estadoRegistro(),
                result.fechaCreacion(),
                result.actorCreacionId(),
                result.fechaModificacion(),
                result.actorModificacionId()
        );
    }
}

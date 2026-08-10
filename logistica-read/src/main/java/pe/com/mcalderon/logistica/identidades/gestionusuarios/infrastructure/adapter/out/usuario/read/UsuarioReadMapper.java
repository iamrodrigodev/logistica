package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.EstadoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;

import java.util.Objects;

public final class UsuarioReadMapper {

    public ConsultarUsuarioPorIdResult aResult(
            UsuarioReadProjection projection
    ) {
        Objects.requireNonNull(
                projection,
                "UsuarioReadProjection no puede ser nula"
        );

        return new ConsultarUsuarioPorIdResult(
                projection.getUsuarioId(),
                projection.getPersonaId(),
                projection.getNombre(),
                projection.getCorreo(),

                OrigenUsuario.desdeCodigo(
                        projection.getOrigenCodigo()
                ),

                TipoCuenta.desdeCodigo(
                        projection.getTipoCuentaCodigo()
                ),

                EstadoCuenta.desdeCodigo(
                        projection.getEstadoCuentaCodigo()
                ),

                projection.getFechaInicioVigencia(),
                projection.getFechaFinVigencia(),
                projection.getEstadoRegistro(),
                projection.getFechaCreacion(),
                projection.getActorCreacionId(),
                projection.getFechaModificacion(),
                projection.getActorModificacionId()
        );
    }

    public ConsultarUsuariosResult aConsultarUsuariosResult(
            ConsultarUsuariosReadProjection projection
    ) {
        Objects.requireNonNull(
                projection,
                "ConsultarUsuariosReadProjection no puede ser nula"
        );

        return new ConsultarUsuariosResult(
                projection.getUsuarioId(),
                projection.getPersonaId(),
                projection.getNombre(),
                projection.getCorreo(),

                OrigenUsuario.desdeCodigo(
                        projection.getOrigenCodigo()
                ),

                TipoCuenta.desdeCodigo(
                        projection.getTipoCuentaCodigo()
                ),

                EstadoCuenta.desdeCodigo(
                        projection.getEstadoCuentaCodigo()
                ),

                projection.getFechaInicioVigencia(),
                projection.getFechaFinVigencia(),
                projection.getEstadoRegistro(),
                projection.getFechaCreacion(),
                projection.getActorCreacionId(),
                projection.getFechaModificacion(),
                projection.getActorModificacionId()
        );
    }
}
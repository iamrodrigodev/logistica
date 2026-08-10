package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.EstadoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioJpaEntity;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.util.Objects;

public final class UsuarioWriteMapper {

    public UsuarioJpaEntity aJpaEntity(
            Usuario usuario
    ) {
        Objects.requireNonNull(
                usuario,
                "Usuario no puede ser nulo"
        );

        return new UsuarioJpaEntity(

                usuario.id() == null
                        ? null
                        : usuario.id().valor(),

                usuario.personaId() == null
                        ? null
                        : usuario.personaId().valor(),

                usuario.nombre(),

                usuario.correo().valor(),

                usuario.origen().codigo(),

                usuario.tipoCuenta().codigo(),

                usuario.estadoCuenta().codigo(),

                usuario.periodoVigencia().fechaInicio(),

                usuario.periodoVigencia().fechaFin(),

                usuario.estadoRegistro(),

                usuario.fechaCreacion(),

                usuario.actorCreacionId().valor(),

                usuario.fechaModificacion(),

                usuario.actorModificacionId() == null
                        ? null
                        : usuario.actorModificacionId().valor()
        );
    }

    public Usuario aDominio(
            UsuarioJpaEntity entity
    ) {
        Objects.requireNonNull(
                entity,
                "UsuarioJpaEntity no puede ser nulo"
        );

        UsuarioId usuarioId =
                new UsuarioId(entity.usuarioId());

        PersonaId personaId =
                entity.personaId() == null
                        ? null
                        : new PersonaId(entity.personaId());

        CorreoElectronico correoElectronico =
                new CorreoElectronico(entity.correo());

        OrigenUsuario origenUsuario =
                OrigenUsuario.desdeCodigo(
                        entity.origenCodigo()
                );

        TipoCuenta tipoCuenta =
                TipoCuenta.desdeCodigo(
                        entity.tipoCuentaCodigo()
                );

        EstadoCuenta estadoCuenta =
                EstadoCuenta.desdeCodigo(
                        entity.estadoCuentaCodigo()
                );

        PeriodoVigencia periodoVigencia =
                new PeriodoVigencia(
                        entity.fechaInicioVigencia(),
                        entity.fechaFinVigencia()
                );

        ActorAuditoriaId actorCreacionId =
                new ActorAuditoriaId(
                        entity.actorCreacionId()
                );

        ActorAuditoriaId actorModificacionId =
                entity.actorModificacionId() == null
                        ? null
                        : new ActorAuditoriaId(
                        entity.actorModificacionId()
                );

        return Usuario.rehidratar(
                usuarioId,
                personaId,
                entity.nombre(),
                correoElectronico,
                origenUsuario,
                tipoCuenta,
                estadoCuenta,
                periodoVigencia,
                entity.estadoRegistro(),
                entity.fechaCreacion(),
                actorCreacionId,
                entity.fechaModificacion(),
                actorModificacionId
        );
    }

}
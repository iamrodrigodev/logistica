package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ConsultarOtroUsuarioConCorreoPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.ConsultarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioJpaEntity;

import java.util.Objects;
import java.util.Optional;

public final class UsuarioWritePersistenceAdapter
        implements GuardarUsuarioPort,
        ConsultarUsuarioPort,
        CargarUsuarioPorIdPort,
        ConsultarOtroUsuarioConCorreoPort,
        EliminarFisicamenteUsuarioPort {

    private final UsuarioWriteRepository repository;
    private final UsuarioWriteMapper mapper;

    public UsuarioWritePersistenceAdapter(
            UsuarioWriteRepository repository,
            UsuarioWriteMapper mapper
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "UsuarioWriteRepository no puede ser nulo"
        );

        this.mapper = Objects.requireNonNull(
                mapper,
                "UsuarioWriteMapper no puede ser nulo"
        );
    }

    @Override
    public boolean existeUsuarioConCorreo(
            CorreoElectronico correoElectronico
    ) {
        Objects.requireNonNull(
                correoElectronico,
                "CorreoElectronico no puede ser nulo"
        );

        return repository.existsByCorreo(
                correoElectronico.valor()
        );
    }

    @Override
    public boolean existeOtroUsuarioConCorreo(
            CorreoElectronico correo,
            UsuarioId usuarioIdExcluido
    ) {
        Objects.requireNonNull(
                correo,
                "CorreoElectronico no puede ser nulo"
        );

        Objects.requireNonNull(
                usuarioIdExcluido,
                "UsuarioId no puede ser nulo"
        );

        return repository
                .existsByCorreoAndUsuarioIdNot(
                        correo.valor(),
                        usuarioIdExcluido.valor()
                );
    }

    @Override
    public UsuarioId guardar(
            Usuario usuario
    ) {
        Objects.requireNonNull(
                usuario,
                "Usuario no puede ser nulo"
        );

        UsuarioJpaEntity entity =
                mapper.aJpaEntity(usuario);

        try {

            UsuarioJpaEntity entityGuardada =
                    repository.saveAndFlush(entity);

            return new UsuarioId(
                    entityGuardada.usuarioId()
            );

        } catch (DataIntegrityViolationException ex) {

            if (esRestriccionCorreoDuplicado(ex)) {
                throw new CorreoElectronicoYaRegistradoException(
                        usuario.correo()
                );
            }

            throw ex;
        }
    }

    private boolean esRestriccionCorreoDuplicado(
            DataIntegrityViolationException exception
    ) {

        Throwable causa = exception.getCause();

        while (causa != null) {

            if (causa instanceof ConstraintViolationException constraintViolationException) {

                return "UQ_Usuario_Correo"
                        .equalsIgnoreCase(
                                constraintViolationException.getConstraintName()
                        );
            }

            causa = causa.getCause();
        }

        return false;
    }


    @Override
    public Optional<Usuario> cargarPorId(
            UsuarioId usuarioId
    ) {
        Objects.requireNonNull(
                usuarioId,
                "UsuarioId no puede ser nulo"
        );

        return repository
                .findById(
                        usuarioId.valor()
                )
                .map(
                        mapper::aDominio
                );
    }

    @Override
    public void eliminarFisicamente(
            UsuarioId usuarioId
    ) {
        Objects.requireNonNull(
                usuarioId,
                "UsuarioId no puede ser nulo"
        );

        repository.deleteById(
                usuarioId.valor()
        );
    }
}
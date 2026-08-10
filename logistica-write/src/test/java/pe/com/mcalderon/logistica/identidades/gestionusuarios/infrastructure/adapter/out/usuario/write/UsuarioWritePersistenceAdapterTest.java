package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioJpaEntity;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioWritePersistenceAdapterTest {

    private UsuarioWriteRepository repository;
    private UsuarioWritePersistenceAdapter adapter;

    @BeforeEach
    void preparar() {

        repository =
                mock(UsuarioWriteRepository.class);

        adapter =
                new UsuarioWritePersistenceAdapter(
                        repository,
                        new UsuarioWriteMapper()
                );
    }

    @Test
    void debeTraducirRestriccionDeCorreoDuplicadoAExcepcionDeAplicacion() {

        Usuario usuario =
                crearUsuarioValido();

        ConstraintViolationException constraintViolationException =
                mock(ConstraintViolationException.class);

        when(
                constraintViolationException.getConstraintName()
        ).thenReturn(
                "UQ_Usuario_Correo"
        );

        DataIntegrityViolationException dataIntegrityException =
                new DataIntegrityViolationException(
                        "Violación de integridad",
                        constraintViolationException
                );

        when(
                repository.saveAndFlush(any())
        ).thenThrow(
                dataIntegrityException
        );

        assertThrows(
                CorreoElectronicoYaRegistradoException.class,
                () -> adapter.guardar(usuario)
        );
    }

    @Test
    void noDebeTraducirUnaRestriccionQueNoSeaLaDelCorreo() {

        Usuario usuario =
                crearUsuarioValido();

        ConstraintViolationException constraintViolationException =
                mock(ConstraintViolationException.class);

        when(
                constraintViolationException.getConstraintName()
        ).thenReturn(
                "FK_Usuario_Persona"
        );

        DataIntegrityViolationException dataIntegrityException =
                new DataIntegrityViolationException(
                        "Violación de integridad",
                        constraintViolationException
                );

        when(
                repository.saveAndFlush(any())
        ).thenThrow(
                dataIntegrityException
        );

        DataIntegrityViolationException exception =
                assertThrows(
                        DataIntegrityViolationException.class,
                        () -> adapter.guardar(usuario)
                );

        assertSame(
                dataIntegrityException,
                exception
        );
    }

    private Usuario crearUsuarioValido() {

        return Usuario.crear(
                new PersonaId(10L),
                "Miguel Calderón",
                new CorreoElectronico(
                        "miguel@empresa.com"
                ),
                OrigenUsuario.INTERNO,
                TipoCuenta.PERSONAL,
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                ),
                LocalDateTime.of(
                        2026,
                        8,
                        8,
                        10,
                        0
                ),
                new ActorAuditoriaId(1L)
        );
    }

    @Test
    void debeGuardarUsuarioYRetornarElIdAsignadoPorPersistencia() {

        Usuario usuario =
                crearUsuarioValido();

        UsuarioJpaEntity entityGuardada =
                new UsuarioJpaEntity(
                        100L,
                        usuario.personaId().valor(),
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

        when(
                repository.saveAndFlush(any(UsuarioJpaEntity.class))
        ).thenReturn(
                entityGuardada
        );

        UsuarioId resultado =
                adapter.guardar(usuario);

        assertEquals(
                100L,
                resultado.valor()
        );
    }
}
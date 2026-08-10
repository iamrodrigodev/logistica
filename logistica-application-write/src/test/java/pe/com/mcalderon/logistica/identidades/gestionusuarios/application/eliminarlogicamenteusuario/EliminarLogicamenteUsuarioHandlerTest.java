package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.EstadoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuarioEstandar;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EliminarLogicamenteUsuarioHandlerTest {

    private static final Instant INSTANTE_FIJO =
            Instant.parse("2026-07-31T14:30:00Z");

    private static final ZoneId ZONA_HORARIA =
            ZoneId.of("America/Lima");

    private PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private CargarUsuarioPorIdPortFake cargarUsuarioPorIdPort;
    private GuardarUsuarioPortFake guardarUsuarioPort;
    private Clock clock;
    private EliminarLogicamenteUsuarioHandler service;

    @BeforeEach
    void setUp() {
        politicaVigenciaUsuario =
                new PoliticaVigenciaUsuarioEstandar();

        cargarUsuarioPorIdPort =
                new CargarUsuarioPorIdPortFake();

        guardarUsuarioPort =
                new GuardarUsuarioPortFake();

        clock = Clock.fixed(
                INSTANTE_FIJO,
                ZONA_HORARIA
        );

        service = new EliminarLogicamenteUsuarioHandler(
                cargarUsuarioPorIdPort,
                guardarUsuarioPort,
                clock
        );
    }

    @Test
    void debeEliminarLogicamenteYGuardarAlUsuario() {
        Usuario usuario = crearUsuarioExistente();

        assertTrue(
                usuario.estaRegistrado()
        );

        cargarUsuarioPorIdPort.configurarUsuario(
                usuario
        );

        EliminarLogicamenteUsuarioCommand command =
                new EliminarLogicamenteUsuarioCommand(
                        1L,
                        99L
                );

        service.eliminarLogicamenteUsuario(
                command
        );

        Usuario usuarioGuardado =
                guardarUsuarioPort.usuarioGuardado();

        assertFalse(
                usuarioGuardado.estaRegistrado()
        );

        assertTrue(
                usuarioGuardado.estaEliminadoLogicamente()
        );

        assertSame(
                usuario,
                usuarioGuardado
        );
    }

    @Test
    void debeFallarCuandoElCommandEsNulo() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.eliminarLogicamenteUsuario(
                                null
                        )
                );

        org.junit.jupiter.api.Assertions.assertEquals(
                "El comando para eliminar lógicamente el usuario no puede ser nulo",
                exception.getMessage()
        );

        assertNull(
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    @Test
    void debeFallarCuandoElUsuarioNoExiste() {
        EliminarLogicamenteUsuarioCommand command =
                new EliminarLogicamenteUsuarioCommand(
                        999L,
                        99L
                );

        assertThrows(
                UsuarioNoExisteException.class,
                () -> service.eliminarLogicamenteUsuario(
                        command
                )
        );

        assertNull(
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    @Test
    void debePropagarLaReglaCuandoElUsuarioYaEstaEliminadoLogicamente() {
        Usuario usuario = crearUsuarioExistente();

        usuario.eliminarLogicamente(
                LocalDateTime.of(
                        2026,
                        7,
                        30,
                        10,
                        0
                ),
                new ActorAuditoriaId(50L)
        );

        cargarUsuarioPorIdPort.configurarUsuario(
                usuario
        );

        EliminarLogicamenteUsuarioCommand command =
                new EliminarLogicamenteUsuarioCommand(
                        1L,
                        99L
                );

        assertThrows(
                RuntimeException.class,
                () -> service.eliminarLogicamenteUsuario(
                        command
                )
        );

        assertNull(
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    private Usuario crearUsuarioExistente() {
        PeriodoVigencia periodoVigencia =
                politicaVigenciaUsuario.calcular(
                        LocalDate.of(
                                2026,
                                1,
                                1
                        ),
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL
                );

        return Usuario.rehidratar(
                new UsuarioId(1L),
                new PersonaId(10L),
                "Miguel Calderón",
                new CorreoElectronico(
                        "miguel@empresa.com"
                ),
                OrigenUsuario.INTERNO,
                TipoCuenta.PERSONAL,
                EstadoCuenta.ACTIVACION_PENDIENTE,
                periodoVigencia,
                true,
                LocalDateTime.of(
                        2026,
                        1,
                        1,
                        9,
                        0
                ),
                new ActorAuditoriaId(1L),
                null,
                null
        );
    }

    private static final class CargarUsuarioPorIdPortFake
            implements CargarUsuarioPorIdPort {

        private Usuario usuario;

        void configurarUsuario(
                Usuario usuario
        ) {
            this.usuario = usuario;
        }

        @Override
        public Optional<Usuario> cargarPorId(
                UsuarioId usuarioId
        ) {
            return Optional.ofNullable(
                    usuario
            );
        }
    }

    private static final class GuardarUsuarioPortFake
            implements GuardarUsuarioPort {

        private Usuario usuarioGuardado;

        @Override
        public UsuarioId guardar(
                Usuario usuario
        ) {
            this.usuarioGuardado = usuario;

            return usuario.id();
        }

        Usuario usuarioGuardado() {
            return usuarioGuardado;
        }
    }
}
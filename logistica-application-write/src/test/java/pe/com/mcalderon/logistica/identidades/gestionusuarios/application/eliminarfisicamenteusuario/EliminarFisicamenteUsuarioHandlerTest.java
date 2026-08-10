package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EliminarFisicamenteUsuarioHandlerTest {

    private PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private CargarUsuarioPorIdPortFake cargarUsuarioPorIdPort;
    private EliminarFisicamenteUsuarioPortFake eliminarFisicamenteUsuarioPort;
    private EliminarFisicamenteUsuarioHandler service;

    @BeforeEach
    void setUp() {
        politicaVigenciaUsuario =
                new PoliticaVigenciaUsuarioEstandar();

        cargarUsuarioPorIdPort =
                new CargarUsuarioPorIdPortFake();

        eliminarFisicamenteUsuarioPort =
                new EliminarFisicamenteUsuarioPortFake();

        service = new EliminarFisicamenteUsuarioHandler(
                cargarUsuarioPorIdPort,
                eliminarFisicamenteUsuarioPort
        );
    }

    @Test
    void debeEliminarFisicamenteAlUsuario() {
        Usuario usuario = crearUsuarioExistente();

        cargarUsuarioPorIdPort.configurarUsuario(
                usuario
        );

        EliminarFisicamenteUsuarioCommand command =
                new EliminarFisicamenteUsuarioCommand(
                        1L
                );

        service.eliminarFisicamenteUsuario(
                command
        );

        assertTrue(
                eliminarFisicamenteUsuarioPort.fueInvocado()
        );

        assertEquals(
                new UsuarioId(1L),
                eliminarFisicamenteUsuarioPort.usuarioIdEliminado()
        );
    }

    @Test
    void debeFallarCuandoElCommandEsNulo() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.eliminarFisicamenteUsuario(
                                null
                        )
                );

        assertEquals(
                "El comando para eliminar físicamente el usuario no puede ser nulo",
                exception.getMessage()
        );

        assertFalse(
                eliminarFisicamenteUsuarioPort.fueInvocado()
        );

        assertNull(
                eliminarFisicamenteUsuarioPort.usuarioIdEliminado()
        );
    }

    @Test
    void debeFallarCuandoElUsuarioNoExiste() {
        EliminarFisicamenteUsuarioCommand command =
                new EliminarFisicamenteUsuarioCommand(
                        999L
                );

        assertThrows(
                UsuarioNoExisteException.class,
                () -> service.eliminarFisicamenteUsuario(
                        command
                )
        );

        assertFalse(
                eliminarFisicamenteUsuarioPort.fueInvocado()
        );

        assertNull(
                eliminarFisicamenteUsuarioPort.usuarioIdEliminado()
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

    private static final class EliminarFisicamenteUsuarioPortFake
            implements EliminarFisicamenteUsuarioPort {

        private boolean invocado;
        private UsuarioId usuarioIdEliminado;

        @Override
        public void eliminarFisicamente(
                UsuarioId usuarioId
        ) {
            this.invocado = true;
            this.usuarioIdEliminado = usuarioId;
        }

        boolean fueInvocado() {
            return invocado;
        }

        UsuarioId usuarioIdEliminado() {
            return usuarioIdEliminado;
        }
    }
}
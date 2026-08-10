package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ModificarUsuarioHandlerTest {

    private static final Instant INSTANTE_FIJO =
            Instant.parse("2026-07-31T05:00:00Z");

    private static final ZoneId ZONA_HORARIA =
            ZoneId.of("America/Lima");

    private CargarUsuarioPorIdPortFake cargarUsuarioPorIdPort;
    private ConsultarOtroUsuarioConCorreoPortFake
            consultarOtroUsuarioConCorreoPort;
    private GuardarUsuarioPortFake guardarUsuarioPort;
    private PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private Clock clock;

    private ModificarUsuarioHandler service;

    @BeforeEach
    void setUp() {
        cargarUsuarioPorIdPort =
                new CargarUsuarioPorIdPortFake();

        consultarOtroUsuarioConCorreoPort =
                new ConsultarOtroUsuarioConCorreoPortFake();

        guardarUsuarioPort =
                new GuardarUsuarioPortFake();

        politicaVigenciaUsuario =
                new PoliticaVigenciaUsuarioEstandar();

        clock = Clock.fixed(
                INSTANTE_FIJO,
                ZONA_HORARIA
        );

        cargarUsuarioPorIdPort.configurarUsuario(
                crearUsuarioExistente()
        );

        service = new ModificarUsuarioHandler(
                cargarUsuarioPorIdPort,
                consultarOtroUsuarioConCorreoPort,
                guardarUsuarioPort,
                politicaVigenciaUsuario,
                clock
        );
    }

    @Test
    void debeModificarYGuardarElUsuario() {
        ModificarUsuarioCommand command =
                crearCommandValido();

        service.modificarUsuario(command);

        Usuario usuarioGuardado =
                guardarUsuarioPort.usuarioGuardado();

        assertNotNull(usuarioGuardado);

        assertEquals(
                new UsuarioId(1L),
                usuarioGuardado.id()
        );

        assertEquals(
                "Miguel Calderón Modificado",
                usuarioGuardado.nombre()
        );

        assertEquals(
                new CorreoElectronico(
                        "miguel.modificado@empresa.com"
                ),
                usuarioGuardado.correo()
        );

        assertEquals(
                OrigenUsuario.INTERNO,
                usuarioGuardado.origen()
        );

        assertEquals(
                TipoCuenta.PERSONAL,
                usuarioGuardado.tipoCuenta()
        );

        assertEquals(
                LocalDate.of(2026, 8, 1),
                usuarioGuardado
                        .periodoVigencia()
                        .fechaInicio()
        );

        assertEquals(
                LocalDate.of(2027, 7, 31),
                usuarioGuardado
                        .periodoVigencia()
                        .fechaFin()
        );

        assertEquals(
                LocalDateTime.of(
                        2026,
                        7,
                        31,
                        0,
                        0
                ),
                usuarioGuardado.fechaModificacion()
        );

        assertEquals(
                new ActorAuditoriaId(2L),
                usuarioGuardado.actorModificacionId()
        );
    }

    @Test
    void debeCargarElUsuarioSolicitado() {
        service.modificarUsuario(
                crearCommandValido()
        );

        assertEquals(
                new UsuarioId(1L),
                cargarUsuarioPorIdPort.usuarioIdConsultado()
        );
    }

    @Test
    void debeValidarElCorreoExcluyendoAlUsuarioActual() {
        service.modificarUsuario(
                crearCommandValido()
        );

        assertEquals(
                new CorreoElectronico(
                        "miguel.modificado@empresa.com"
                ),
                consultarOtroUsuarioConCorreoPort
                        .correoConsultado()
        );

        assertEquals(
                new UsuarioId(1L),
                consultarOtroUsuarioConCorreoPort
                        .usuarioIdExcluido()
        );
    }

    @Test
    void debeGuardarElMismoAgregadoQueFueCargado() {
        Usuario usuarioCargado =
                cargarUsuarioPorIdPort.usuarioConfigurado();

        service.modificarUsuario(
                crearCommandValido()
        );

        assertSame(
                usuarioCargado,
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    private ModificarUsuarioCommand crearCommandValido() {
        return new ModificarUsuarioCommand(
                1L,
                "Miguel Calderón Modificado",
                "miguel.modificado@empresa.com",
                'I',
                'P',
                LocalDate.of(2026, 8, 1),
                2L
        );
    }

    private Usuario crearUsuarioExistente() {
        PeriodoVigencia periodoVigencia =
                politicaVigenciaUsuario.calcular(
                        LocalDate.of(2026, 1, 1),
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
        private UsuarioId usuarioIdConsultado;

        @Override
        public Optional<Usuario> cargarPorId(
                UsuarioId usuarioId
        ) {
            this.usuarioIdConsultado = usuarioId;

            return Optional.ofNullable(usuario);
        }

        void configurarUsuario(
                Usuario usuario
        ) {
            this.usuario = usuario;
        }

        Usuario usuarioConfigurado() {
            return usuario;
        }

        UsuarioId usuarioIdConsultado() {
            return usuarioIdConsultado;
        }
    }

    private static final class
    ConsultarOtroUsuarioConCorreoPortFake
            implements ConsultarOtroUsuarioConCorreoPort {

        private boolean correoExistente;
        private CorreoElectronico correoConsultado;
        private UsuarioId usuarioIdExcluido;

        @Override
        public boolean existeOtroUsuarioConCorreo(
                CorreoElectronico correo,
                UsuarioId usuarioIdExcluido
        ) {
            this.correoConsultado = correo;
            this.usuarioIdExcluido = usuarioIdExcluido;

            return correoExistente;
        }

        void configurarCorreoExistente(
                boolean correoExistente
        ) {
            this.correoExistente = correoExistente;
        }

        CorreoElectronico correoConsultado() {
            return correoConsultado;
        }

        UsuarioId usuarioIdExcluido() {
            return usuarioIdExcluido;
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
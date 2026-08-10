package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.PersonaNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuarioEstandar;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class CrearUsuarioHandlerTest {

    private static final Instant INSTANTE_FIJO =
            Instant.parse("2026-07-24T15:30:00Z");

    private static final ZoneId ZONA_HORARIA =
            ZoneId.of("America/Lima");

    private ConsultarUsuarioPortFake consultarUsuarioPort;
    private GuardarUsuarioPortFake guardarUsuarioPort;

    // NUEVO: fake para consultar personas
    private ConsultarPersonaPortFake consultarPersonaPort;

    private PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private Clock clock;
    private CrearUsuarioHandler service;

    @BeforeEach
    void preparar() {
        consultarUsuarioPort =
                new ConsultarUsuarioPortFake();

        guardarUsuarioPort =
                new GuardarUsuarioPortFake(
                        new UsuarioId(100L)
                );

        // NUEVO: por defecto la persona existe
        consultarPersonaPort =
                new ConsultarPersonaPortFake();

        politicaVigenciaUsuario =
                new PoliticaVigenciaUsuarioEstandar();

        clock = Clock.fixed(
                INSTANTE_FIJO,
                ZONA_HORARIA
        );

        service = new CrearUsuarioHandler(
                consultarUsuarioPort,
                guardarUsuarioPort,
                consultarPersonaPort,
                politicaVigenciaUsuario,
                clock
        );
    }

    @Test
    void debeCrearGuardarYDevolverElIdentificadorDelUsuario() {
        CrearUsuarioCommand command =
                crearCommandValido();

        CrearUsuarioResult resultado =
                service.crearUsuario(command);

        assertEquals(
                new CrearUsuarioResult(100L),
                resultado
        );

        assertNotNull(
                guardarUsuarioPort.usuarioGuardado()
        );

        assertTrue(
                guardarUsuarioPort.usuarioGuardado()
                        .estaPendienteDeActivacion()
        );

        assertTrue(
                guardarUsuarioPort.usuarioGuardado()
                        .esCuentaPersonal()
        );

        assertTrue(
                guardarUsuarioPort.usuarioGuardado()
                        .esInterno()
        );
    }

    @Test
    void debeNormalizarElCorreoAntesDeConsultarlo() {
        CrearUsuarioCommand command =
                new CrearUsuarioCommand(
                        10L,
                        "Miguel Calderón",
                        "  MIGUEL@EMPRESA.COM  ",
                        'I',
                        'P',
                        LocalDate.of(2026, 1, 1),
                        1L
                );

        service.crearUsuario(command);

        assertEquals(
                new CorreoElectronico(
                        "miguel@empresa.com"
                ),
                consultarUsuarioPort.correoConsultado()
        );
    }

    @Test
    void debeUtilizarLaFechaDelClockParaLaAuditoria() {
        service.crearUsuario(
                crearCommandValido()
        );

        Usuario usuarioGuardado =
                guardarUsuarioPort.usuarioGuardado();

        LocalDateTime fechaEsperada =
                LocalDateTime.ofInstant(
                        INSTANTE_FIJO,
                        ZONA_HORARIA
                );

        assertEquals(
                fechaEsperada,
                usuarioGuardado.fechaCreacion()
        );
    }

    @Test
    void debeCalcularLaVigenciaSegunOrigenYTipoDeCuenta() {
        service.crearUsuario(
                crearCommandValido()
        );

        Usuario usuarioGuardado =
                guardarUsuarioPort.usuarioGuardado();

        assertTrue(
                usuarioGuardado.estaVigente(
                        LocalDate.of(2026, 12, 31)
                )
        );

        assertFalse(
                usuarioGuardado.estaVigente(
                        LocalDate.of(2027, 1, 1)
                )
        );
    }

    @Test
    void noDebeGuardarCuandoElCorreoYaEstaRegistrado() {
        consultarUsuarioPort.configurarCorreoExistente(true);

        CrearUsuarioCommand command =
                crearCommandValido();

        assertThrows(
                CorreoElectronicoYaRegistradoException.class,
                () -> service.crearUsuario(command)
        );

        assertNull(
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    @Test
    void debeInformarElCorreoDuplicadoEnLaExcepcion() {
        consultarUsuarioPort.configurarCorreoExistente(true);

        CorreoElectronicoYaRegistradoException excepcion =
                assertThrows(
                        CorreoElectronicoYaRegistradoException.class,
                        () -> service.crearUsuario(
                                crearCommandValido()
                        )
                );

        assertEquals(
                "Ya existe un usuario registrado con el correo electrónico: "
                        + "miguel@empresa.com",
                excepcion.getMessage()
        );
    }

    /*
     * Nuevas pruebas relacionadas con Persona.
     */

    @Test
    void debeConsultarLaExistenciaDeLaPersona() {
        service.crearUsuario(
                crearCommandValido()
        );

        assertEquals(
                new PersonaId(10L),
                consultarPersonaPort.personaConsultada()
        );
    }

    @Test
    void noDebeGuardarCuandoLaPersonaNoExiste() {
        consultarPersonaPort.configurarPersonaExistente(false);

        PersonaNoExisteException excepcion =
                assertThrows(
                        PersonaNoExisteException.class,
                        () -> service.crearUsuario(
                                crearCommandValido()
                        )
                );

        assertEquals(
                "No existe una persona registrada con el identificador: 10",
                excepcion.getMessage()
        );

        assertNull(
                guardarUsuarioPort.usuarioGuardado()
        );
    }

    @Test
    void debePermitirCuentaDeServicioSinPersona() {
        CrearUsuarioCommand command =
                new CrearUsuarioCommand(
                        null,
                        "Cuenta técnica",
                        "servicio@empresa.com",
                        'I',
                        'S',
                        LocalDate.of(2026, 1, 1),
                        1L
                );

        CrearUsuarioResult resultado =
                service.crearUsuario(command);

        assertEquals(
                new CrearUsuarioResult(100L),
                resultado
        );

        assertNull(
                consultarPersonaPort.personaConsultada()
        );

        assertNotNull(
                guardarUsuarioPort.usuarioGuardado()
        );

        assertTrue(
                guardarUsuarioPort.usuarioGuardado()
                        .esCuentaServicio()
        );
    }

    @Test
    void noDebeAceptarUnCommandNulo() {
        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.crearUsuario(null)
                );

        assertEquals(
                "El comando para crear el usuario no puede ser nulo",
                excepcion.getMessage()
        );
    }

    /*
     * Pruebas del constructor.
     * Todas deben pasar ahora cinco argumentos.
     */

    @Test
    void constructorNoDebeAceptarConsultarUsuarioPortNulo() {
        assertThrows(
                NullPointerException.class,
                () -> new CrearUsuarioHandler(
                        null,
                        guardarUsuarioPort,
                        consultarPersonaPort,
                        politicaVigenciaUsuario,
                        clock
                )
        );
    }

    @Test
    void constructorNoDebeAceptarGuardarUsuarioPortNulo() {
        assertThrows(
                NullPointerException.class,
                () -> new CrearUsuarioHandler(
                        consultarUsuarioPort,
                        null,
                        consultarPersonaPort,
                        politicaVigenciaUsuario,
                        clock
                )
        );
    }

    @Test
    void constructorNoDebeAceptarConsultarPersonaPortNulo() {
        assertThrows(
                NullPointerException.class,
                () -> new CrearUsuarioHandler(
                        consultarUsuarioPort,
                        guardarUsuarioPort,
                        null,
                        politicaVigenciaUsuario,
                        clock
                )
        );
    }

    @Test
    void constructorNoDebeAceptarPoliticaDeVigenciaNula() {
        assertThrows(
                NullPointerException.class,
                () -> new CrearUsuarioHandler(
                        consultarUsuarioPort,
                        guardarUsuarioPort,
                        consultarPersonaPort,
                        null,
                        clock
                )
        );
    }

    @Test
    void constructorNoDebeAceptarClockNulo() {
        assertThrows(
                NullPointerException.class,
                () -> new CrearUsuarioHandler(
                        consultarUsuarioPort,
                        guardarUsuarioPort,
                        consultarPersonaPort,
                        politicaVigenciaUsuario,
                        null
                )
        );
    }

    private CrearUsuarioCommand crearCommandValido() {
        return new CrearUsuarioCommand(
                10L,
                "Miguel Calderón",
                "miguel@empresa.com",
                'I',
                'P',
                LocalDate.of(2026, 1, 1),
                1L
        );
    }

    /*
     * Fake de consulta de usuarios:
     * sustituye temporalmente al futuro adaptador SQL Server/JPA.
     */
    private static final class ConsultarUsuarioPortFake
            implements ConsultarUsuarioPort {

        private boolean correoExistente;
        private CorreoElectronico correoConsultado;

        @Override
        public boolean existeUsuarioConCorreo(
                CorreoElectronico correo
        ) {
            this.correoConsultado = correo;
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
    }

    /*
     * NUEVO:
     * Fake de consulta de personas.
     */
    private static final class ConsultarPersonaPortFake
            implements ConsultarPersonaPort {

        /*
         * Se inicia en true para que las pruebas anteriores
         * continúen considerando que PersonaId(10) existe.
         */
        private boolean personaExistente = true;
        private PersonaId personaConsultada;

        @Override
        public boolean existePersona(
                PersonaId personaId
        ) {
            this.personaConsultada = personaId;
            return personaExistente;
        }

        void configurarPersonaExistente(
                boolean personaExistente
        ) {
            this.personaExistente = personaExistente;
        }

        PersonaId personaConsultada() {
            return personaConsultada;
        }
    }

    /*
     * Fake de guardado:
     * captura el Usuario que Application intentó persistir
     * y devuelve un identificador simulado.
     */
    private static final class GuardarUsuarioPortFake
            implements GuardarUsuarioPort {

        private final UsuarioId usuarioIdGenerado;
        private Usuario usuarioGuardado;

        private GuardarUsuarioPortFake(
                UsuarioId usuarioIdGenerado
        ) {
            this.usuarioIdGenerado = usuarioIdGenerado;
        }

        @Override
        public UsuarioId guardar(
                Usuario usuario
        ) {
            this.usuarioGuardado = usuario;
            return usuarioIdGenerado;
        }

        Usuario usuarioGuardado() {
            return usuarioGuardado;
        }
    }
}
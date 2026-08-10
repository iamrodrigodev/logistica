package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private static final LocalDateTime FECHA_CREACION =
            LocalDateTime.of(
                    2026,
                    7,
                    24,
                    10,
                    30
            );

    private static final ActorAuditoriaId ACTOR_CREACION_ID =
            new ActorAuditoriaId(1L);

    @Test
    void debeCrearUnUsuarioPendienteDeActivacion() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(usuario.estaPendienteDeActivacion());
        assertFalse(usuario.estaActivo());
        assertFalse(usuario.estaInactivo());
        assertFalse(usuario.estaSuspendido());
        assertFalse(usuario.estaCaducado());
        assertFalse(usuario.estaDadoDeBaja());
    }

    @Test
    void debeIdentificarUnaCuentaPersonal() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(usuario.esCuentaPersonal());
        assertFalse(usuario.esCuentaAdministracion());
        assertFalse(usuario.esCuentaServicio());
        assertFalse(usuario.esCuentaOtro());
    }

    @Test
    void debeIdentificarUnUsuarioInterno() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(usuario.esInterno());
        assertFalse(usuario.esExterno());
    }

    @Test
    void debeIdentificarUnUsuarioExterno() {
        Usuario usuario = Usuario.crear(
                null,
                "Cuenta de servicio externa",
                new CorreoElectronico(
                        "servicio@proveedor.com"
                ),
                OrigenUsuario.EXTERNO,
                TipoCuenta.SERVICIO,
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 3, 1)
                ),
                FECHA_CREACION,
                ACTOR_CREACION_ID
        );

        assertTrue(usuario.esExterno());
        assertFalse(usuario.esInterno());
        assertTrue(usuario.esCuentaServicio());
    }

    @Test
    void debeResponderSiEstaVigente() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(
                usuario.estaVigente(
                        LocalDate.of(2026, 6, 15)
                )
        );

        assertFalse(
                usuario.estaVigente(
                        LocalDate.of(2027, 1, 1)
                )
        );
    }

    @Test
    void usuarioPendienteYVigentePuedeIntentarAutenticarse() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(
                usuario.puedeIntentarAutenticarse(
                        LocalDate.of(2026, 6, 15)
                )
        );
    }

    @Test
    void usuarioPendienteFueraDeVigenciaNoPuedeIntentarAutenticarse() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertFalse(
                usuario.puedeIntentarAutenticarse(
                        LocalDate.of(2027, 1, 1)
                )
        );
    }

    @Test
    void debeCrearElRegistroDeAuditoriaInicial() {
        Usuario usuario = crearUsuarioPersonalInterno();

        assertTrue(usuario.estadoRegistro());
        assertEquals(
                FECHA_CREACION,
                usuario.fechaCreacion()
        );
        assertEquals(
                ACTOR_CREACION_ID,
                usuario.actorCreacionId()
        );
        assertNull(usuario.fechaModificacion());
        assertNull(usuario.actorModificacionId());
    }

    @Test
    void noDebeCrearUsuarioSinNombre() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        " ",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        periodoAnual(),
                        FECHA_CREACION,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinCorreo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        null,
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        periodoAnual(),
                        FECHA_CREACION,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinOrigen() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        null,
                        TipoCuenta.PERSONAL,
                        periodoAnual(),
                        FECHA_CREACION,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinTipoDeCuenta() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        OrigenUsuario.INTERNO,
                        null,
                        periodoAnual(),
                        FECHA_CREACION,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinPeriodoDeVigencia() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        null,
                        FECHA_CREACION,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinFechaDeCreacion() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        periodoAnual(),
                        null,
                        ACTOR_CREACION_ID
                )
        );
    }

    @Test
    void noDebeCrearUsuarioSinActorDeCreacion() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.crear(
                        new PersonaId(10L),
                        "Miguel Calderón",
                        new CorreoElectronico(
                                "miguel@empresa.com"
                        ),
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        periodoAnual(),
                        FECHA_CREACION,
                        null
                )
        );
    }

    @Test
    void cuentaPersonalDebeEstarAsociadaAUnaPersona() {
        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> Usuario.crear(
                                null,
                                "Miguel Calderón",
                                new CorreoElectronico(
                                        "miguel@empresa.com"
                                ),
                                OrigenUsuario.INTERNO,
                                TipoCuenta.PERSONAL,
                                periodoAnual(),
                                FECHA_CREACION,
                                ACTOR_CREACION_ID
                        )
                );

        assertEquals(
                "Una cuenta personal debe estar asociada a una persona",
                excepcion.getMessage()
        );
    }

    @Test
    void cuentaDeServicioPuedeNoEstarAsociadaAUnaPersona() {
        Usuario usuario = Usuario.crear(
                null,
                "Cuenta técnica",
                new CorreoElectronico(
                        "servicio@empresa.com"
                ),
                OrigenUsuario.INTERNO,
                TipoCuenta.SERVICIO,
                periodoAnual(),
                FECHA_CREACION,
                ACTOR_CREACION_ID
        );

        assertTrue(usuario.esCuentaServicio());
        assertTrue(usuario.esInterno());
        assertTrue(usuario.estaPendienteDeActivacion());
    }

    @Test
    void debeDarDeBajaAlUsuario() {
        Usuario usuario = crearUsuarioPersonalInterno();

        LocalDateTime fechaModificacion =
                LocalDateTime.of(
                        2026,
                        7,
                        31,
                        8,
                        45
                );

        ActorAuditoriaId actorModificacionId =
                new ActorAuditoriaId(2L);

        usuario.darDeBaja(
                fechaModificacion,
                actorModificacionId
        );

        assertTrue(usuario.estaDadoDeBaja());

        assertEquals(
                fechaModificacion,
                usuario.fechaModificacion()
        );

        assertEquals(
                actorModificacionId,
                usuario.actorModificacionId()
        );
    }

    @Test
    void noDebeDarDeBajaDosVecesAlUsuario() {
        Usuario usuario = crearUsuarioPersonalInterno();

        LocalDateTime primeraFechaModificacion =
                LocalDateTime.of(
                        2026,
                        7,
                        31,
                        8,
                        45
                );

        ActorAuditoriaId primerActorModificacionId =
                new ActorAuditoriaId(2L);

        usuario.darDeBaja(
                primeraFechaModificacion,
                primerActorModificacionId
        );

        IllegalStateException excepcion =
                assertThrows(
                        IllegalStateException.class,
                        () -> usuario.darDeBaja(
                                LocalDateTime.of(
                                        2026,
                                        7,
                                        31,
                                        9,
                                        0
                                ),
                                new ActorAuditoriaId(3L)
                        )
                );

        assertEquals(
                "El usuario ya se encuentra dado de baja",
                excepcion.getMessage()
        );

        assertEquals(
                primeraFechaModificacion,
                usuario.fechaModificacion()
        );

        assertEquals(
                primerActorModificacionId,
                usuario.actorModificacionId()
        );
    }

    @Test
    void debeEliminarLogicamenteAlUsuario() {
        Usuario usuario = crearUsuarioPersonalInterno();

        LocalDateTime fechaEliminacion =
                LocalDateTime.of(
                        2026,
                        7,
                        31,
                        9,
                        15
                );

        ActorAuditoriaId actorEliminacionId =
                new ActorAuditoriaId(2L);

        usuario.eliminarLogicamente(
                fechaEliminacion,
                actorEliminacionId
        );

        assertTrue(usuario.estaEliminadoLogicamente());

        assertFalse(usuario.estaRegistrado());

        assertEquals(
                fechaEliminacion,
                usuario.fechaModificacion()
        );

        assertEquals(
                actorEliminacionId,
                usuario.actorModificacionId()
        );
    }

    @Test
    void noDebeEliminarLogicamenteDosVecesAlUsuario() {
        Usuario usuario = crearUsuarioPersonalInterno();

        LocalDateTime primeraFechaEliminacion =
                LocalDateTime.of(
                        2026,
                        7,
                        31,
                        9,
                        15
                );

        ActorAuditoriaId primerActorEliminacionId =
                new ActorAuditoriaId(2L);

        usuario.eliminarLogicamente(
                primeraFechaEliminacion,
                primerActorEliminacionId
        );

        IllegalStateException excepcion =
                assertThrows(
                        IllegalStateException.class,
                        () -> usuario.eliminarLogicamente(
                                LocalDateTime.of(
                                        2026,
                                        7,
                                        31,
                                        9,
                                        30
                                ),
                                new ActorAuditoriaId(3L)
                        )
                );

        assertEquals(
                "El usuario ya se encuentra eliminado lógicamente",
                excepcion.getMessage()
        );

        assertEquals(
                primeraFechaEliminacion,
                usuario.fechaModificacion()
        );

        assertEquals(
                primerActorEliminacionId,
                usuario.actorModificacionId()
        );
    }


    private Usuario crearUsuarioPersonalInterno() {
        return Usuario.crear(
                new PersonaId(10L),
                "Miguel Calderón",
                new CorreoElectronico(
                        "miguel@empresa.com"
                ),
                OrigenUsuario.INTERNO,
                TipoCuenta.PERSONAL,
                periodoAnual(),
                FECHA_CREACION,
                ACTOR_CREACION_ID
        );
    }

    private PeriodoVigencia periodoAnual() {
        return new PeriodoVigencia(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
    }
}
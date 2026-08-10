package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.EntidadAuditada;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Usuario extends EntidadAuditada {

    private final UsuarioId id;
    private final PersonaId personaId;

    private String nombre;
    private CorreoElectronico correo;
    private OrigenUsuario origen;
    private TipoCuenta tipoCuenta;
    private EstadoCuenta estadoCuenta;
    private PeriodoVigencia periodoVigencia;

    /*
     * Constructor utilizado para crear un usuario nuevo.
     */
    private Usuario(
            UsuarioId id,
            PersonaId personaId,
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            EstadoCuenta estadoCuenta,
            PeriodoVigencia periodoVigencia,
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId
    ) {
        super(fechaCreacion, actorCreacionId);

        this.id = id;
        this.personaId = personaId;
        this.nombre = nombre;
        this.correo = correo;
        this.origen = origen;
        this.tipoCuenta = tipoCuenta;
        this.estadoCuenta = estadoCuenta;
        this.periodoVigencia = periodoVigencia;
    }

    /*
     * Constructor utilizado para reconstruir un usuario existente
     * desde persistencia.
     */
    private Usuario(
            UsuarioId id,
            PersonaId personaId,
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            EstadoCuenta estadoCuenta,
            PeriodoVigencia periodoVigencia,
            boolean estadoRegistro,
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId,
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        super(
                estadoRegistro,
                fechaCreacion,
                actorCreacionId,
                fechaModificacion,
                actorModificacionId
        );

        this.id = id;
        this.personaId = personaId;
        this.nombre = nombre;
        this.correo = correo;
        this.origen = origen;
        this.tipoCuenta = tipoCuenta;
        this.estadoCuenta = estadoCuenta;
        this.periodoVigencia = periodoVigencia;
    }

    // Fábricas estáticas

    public static Usuario crear(
            PersonaId personaId,
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            PeriodoVigencia periodoVigencia,
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId
    ) {
        validarDatosUsuario(
                personaId,
                nombre,
                correo,
                origen,
                tipoCuenta,
                periodoVigencia
        );

        if (fechaCreacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de creación del usuario no puede ser nula"
            );
        }

        if (actorCreacionId == null) {
            throw new IllegalArgumentException(
                    "El actor de creación del usuario no puede ser nulo"
            );
        }

        return new Usuario(
                null,
                personaId,
                nombre.trim(),
                correo,
                origen,
                tipoCuenta,
                EstadoCuenta.ACTIVACION_PENDIENTE,
                periodoVigencia,
                fechaCreacion,
                actorCreacionId
        );
    }

    public static Usuario rehidratar(
            UsuarioId id,
            PersonaId personaId,
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            EstadoCuenta estadoCuenta,
            PeriodoVigencia periodoVigencia,
            boolean estadoRegistro,
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId,
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario no puede ser nulo"
            );
        }

        validarDatosUsuario(
                personaId,
                nombre,
                correo,
                origen,
                tipoCuenta,
                periodoVigencia
        );

        if (estadoCuenta == null) {
            throw new IllegalArgumentException(
                    "El estado de la cuenta no puede ser nulo"
            );
        }

        return new Usuario(
                id,
                personaId,
                nombre.trim(),
                correo,
                origen,
                tipoCuenta,
                estadoCuenta,
                periodoVigencia,
                estadoRegistro,
                fechaCreacion,
                actorCreacionId,
                fechaModificacion,
                actorModificacionId
        );
    }

    // Comportamiento del dominio

    public void modificar(
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            PeriodoVigencia periodoVigencia,
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        validarDatosUsuario(
                personaId,
                nombre,
                correo,
                origen,
                tipoCuenta,
                periodoVigencia
        );

        /*
         * La auditoría se valida antes de cambiar el estado de la entidad.
         * Así evitamos dejar cambios parciales si la fecha o el actor
         * de modificación fueran inválidos.
         */
        registrarModificacion(
                fechaModificacion,
                actorModificacionId
        );

        this.nombre = nombre.trim();
        this.correo = correo;
        this.origen = origen;
        this.tipoCuenta = tipoCuenta;
        this.periodoVigencia = periodoVigencia;
    }

    public void darDeBaja(
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        if (estaDadoDeBaja()) {
            throw new IllegalStateException(
                    "El usuario ya se encuentra dado de baja"
            );
        }

        /*
         * La auditoría se registra antes de cambiar el estado.
         * Así evitamos dejar el agregado parcialmente modificado
         * cuando la fecha o el actor sean inválidos.
         */
        registrarModificacion(
                fechaModificacion,
                actorModificacionId
        );

        this.estadoCuenta = EstadoCuenta.BAJA;
    }

    public void eliminarLogicamente(
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        if (estaEliminadoLogicamente()) {
            throw new IllegalStateException(
                    "El usuario ya se encuentra eliminado lógicamente"
            );
        }

        /*
         * EntidadAuditada registra la modificación y cambia
         * estadoRegistro a false de forma atómica.
         */
        eliminarRegistro(
                fechaModificacion,
                actorModificacionId
        );
    }

    // Consultas del dominio

    // Estado del registro

    public boolean estaRegistrado() {
        return estadoRegistro();
    }

    public boolean estaEliminadoLogicamente() {
        return !estadoRegistro();
    }

    // Estados del usuario

    public boolean estaActivo() {
        return estadoCuenta == EstadoCuenta.ACTIVO;
    }

    public boolean estaPendienteDeActivacion() {
        return estadoCuenta == EstadoCuenta.ACTIVACION_PENDIENTE;
    }

    public boolean estaSuspendido() {
        return estadoCuenta == EstadoCuenta.SUSPENDIDO;
    }

    public boolean estaCaducado() {
        return estadoCuenta == EstadoCuenta.CADUCADO;
    }

    public boolean estaDadoDeBaja() {
        return estadoCuenta == EstadoCuenta.BAJA;
    }

    public boolean estaInactivo() {
        return estadoCuenta == EstadoCuenta.INACTIVO;
    }

    // Tipo de cuenta

    public boolean esCuentaPersonal() {
        return tipoCuenta == TipoCuenta.PERSONAL;
    }

    public boolean esCuentaAdministracion() {
        return tipoCuenta == TipoCuenta.ADMINISTRACION;
    }

    public boolean esCuentaServicio() {
        return tipoCuenta == TipoCuenta.SERVICIO;
    }

    public boolean esCuentaOtro() {
        return tipoCuenta == TipoCuenta.OTRO;
    }

    // Origen de cuenta

    public boolean esInterno() {
        return origen == OrigenUsuario.INTERNO;
    }

    public boolean esExterno() {
        return origen == OrigenUsuario.EXTERNO;
    }

    // Vigencia de cuenta

    public boolean estaVigente(LocalDate fechaEvaluacion) {
        return periodoVigencia.estaVigente(fechaEvaluacion);
    }

    // Reglas compuestas del negocio

    public boolean puedeIntentarAutenticarse(
            LocalDate fechaEvaluacion
    ) {
        return (estaActivo() || estaPendienteDeActivacion())
                && estaVigente(fechaEvaluacion);
    }

    // Lectura controlada del estado persistible

    public UsuarioId id() {
        return id;
    }

    public PersonaId personaId() {
        return personaId;
    }

    public String nombre() {
        return nombre;
    }

    public CorreoElectronico correo() {
        return correo;
    }

    public OrigenUsuario origen() {
        return origen;
    }

    public TipoCuenta tipoCuenta() {
        return tipoCuenta;
    }

    public EstadoCuenta estadoCuenta() {
        return estadoCuenta;
    }

    public PeriodoVigencia periodoVigencia() {
        return periodoVigencia;
    }

    // Validaciones compartidas

    private static void validarDatosUsuario(
            PersonaId personaId,
            String nombre,
            CorreoElectronico correo,
            OrigenUsuario origen,
            TipoCuenta tipoCuenta,
            PeriodoVigencia periodoVigencia
    ) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del usuario no puede ser nulo ni estar vacío"
            );
        }

        if (correo == null) {
            throw new IllegalArgumentException(
                    "El correo electrónico del usuario no puede ser nulo"
            );
        }

        if (origen == null) {
            throw new IllegalArgumentException(
                    "El origen del usuario no puede ser nulo"
            );
        }

        if (tipoCuenta == null) {
            throw new IllegalArgumentException(
                    "El tipo de cuenta del usuario no puede ser nulo"
            );
        }

        if (periodoVigencia == null) {
            throw new IllegalArgumentException(
                    "El periodo de vigencia del usuario no puede ser nulo"
            );
        }

        if (tipoCuenta == TipoCuenta.PERSONAL && personaId == null) {
            throw new IllegalArgumentException(
                    "Una cuenta personal debe estar asociada a una persona"
            );
        }
    }
}
package pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model;

import java.time.LocalDateTime;

public abstract class EntidadAuditada {

    private boolean estadoRegistro;
    private LocalDateTime fechaCreacion;
    private ActorAuditoriaId actorCreacionId;
    private LocalDateTime fechaModificacion;
    private ActorAuditoriaId actorModificacionId;

    protected EntidadAuditada(
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId
    ) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de creación no puede ser nula"
            );
        }

        if (actorCreacionId == null) {
            throw new IllegalArgumentException(
                    "El actor de creación no puede ser nulo"
            );
        }

        this.estadoRegistro = true;
        this.fechaCreacion = fechaCreacion;
        this.actorCreacionId = actorCreacionId;
        this.fechaModificacion = null;
        this.actorModificacionId = null;
    }

    protected EntidadAuditada(
            boolean estadoRegistro,
            LocalDateTime fechaCreacion,
            ActorAuditoriaId actorCreacionId,
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de creación no puede ser nula"
            );
        }

        if (actorCreacionId == null) {
            throw new IllegalArgumentException(
                    "El actor de creación no puede ser nulo"
            );
        }

        if (fechaModificacion != null && actorModificacionId == null) {
            throw new IllegalArgumentException(
                    "El actor de modificación es obligatorio "
                            + "cuando existe una fecha de modificación"
            );
        }

        if (fechaModificacion == null && actorModificacionId != null) {
            throw new IllegalArgumentException(
                    "La fecha de modificación es obligatoria "
                            + "cuando existe un actor de modificación"
            );
        }

        if (fechaModificacion != null
                && fechaModificacion.isBefore(fechaCreacion)) {
            throw new IllegalArgumentException(
                    "La fecha de modificación no puede ser anterior "
                            + "a la fecha de creación"
            );
        }

        this.estadoRegistro = estadoRegistro;
        this.fechaCreacion = fechaCreacion;
        this.actorCreacionId = actorCreacionId;
        this.fechaModificacion = fechaModificacion;
        this.actorModificacionId = actorModificacionId;
    }

    public boolean estadoRegistro() {
        return estadoRegistro;
    }

    public LocalDateTime fechaCreacion() {
        return fechaCreacion;
    }

    public ActorAuditoriaId actorCreacionId() {
        return actorCreacionId;
    }

    public LocalDateTime fechaModificacion() {
        return fechaModificacion;
    }

    public ActorAuditoriaId actorModificacionId() {
        return actorModificacionId;
    }

    protected void registrarModificacion(
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        if (fechaModificacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de modificación no puede ser nula"
            );
        }

        if (actorModificacionId == null) {
            throw new IllegalArgumentException(
                    "El actor de modificación no puede ser nulo"
            );
        }

        if (fechaModificacion.isBefore(fechaCreacion)) {
            throw new IllegalArgumentException(
                    "La fecha de modificación no puede ser anterior "
                            + "a la fecha de creación"
            );
        }

        this.fechaModificacion = fechaModificacion;
        this.actorModificacionId = actorModificacionId;
    }

    protected void eliminarRegistro(
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        registrarModificacion(fechaModificacion, actorModificacionId);
        this.estadoRegistro = false;
    }

    protected void restaurarRegistro(
            LocalDateTime fechaModificacion,
            ActorAuditoriaId actorModificacionId
    ) {
        registrarModificacion(fechaModificacion, actorModificacionId);
        this.estadoRegistro = true;
    }
}
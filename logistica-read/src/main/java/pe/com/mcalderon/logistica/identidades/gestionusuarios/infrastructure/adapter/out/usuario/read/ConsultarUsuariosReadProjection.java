package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ConsultarUsuariosReadProjection {

    Long getUsuarioId();
    Long getPersonaId();
    String getNombre();
    String getCorreo();
    Character getOrigenCodigo();
    Character getTipoCuentaCodigo();
    Integer getEstadoCuentaCodigo();
    LocalDate getFechaInicioVigencia();
    LocalDate getFechaFinVigencia();
    Boolean getEstadoRegistro();
    LocalDateTime getFechaCreacion();
    Long getActorCreacionId();
    LocalDateTime getFechaModificacion();
    Long getActorModificacionId();
}
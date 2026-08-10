package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario", schema = "public")
public class UsuarioReadJpaEntity {

    @Id
    @Column(name = "UsuarioId")
    private Long usuarioId;

    @Column(name = "PersonaId")
    private Long personaId;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "OrigenCodigo")
    private Character origenCodigo;

    @Column(name = "TipoCuentaCodigo")
    private Character tipoCuentaCodigo;

    @Column(name = "EstadoCuentaCodigo")
    private Integer estadoCuentaCodigo;

    @Column(name = "FechaInicioVigencia")
    private LocalDate fechaInicioVigencia;

    @Column(name = "FechaFinVigencia")
    private LocalDate fechaFinVigencia;

    @Column(name = "EstadoRegistro")
    private Boolean estadoRegistro;

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ActorCreacionId")
    private Long actorCreacionId;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "ActorModificacionId")
    private Long actorModificacionId;


    protected UsuarioReadJpaEntity() {
        // Requerido por JPA
    }


    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Character getOrigenCodigo() {
        return origenCodigo;
    }

    public Character getTipoCuentaCodigo() {
        return tipoCuentaCodigo;
    }

    public Integer getEstadoCuentaCodigo() {
        return estadoCuentaCodigo;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public LocalDate getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public Boolean getEstadoRegistro() {
        return estadoRegistro;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Long getActorCreacionId() {
        return actorCreacionId;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public Long getActorModificacionId() {
        return actorModificacionId;
    }
}
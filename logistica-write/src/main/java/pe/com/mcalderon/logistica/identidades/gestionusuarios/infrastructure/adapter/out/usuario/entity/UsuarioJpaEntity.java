package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "usuario",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_Usuario_Correo",
                        columnNames = "Correo"
                )
        }
)
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioId", nullable = false)
    private Long usuarioId;

    @Column(name = "PersonaId")
    private Long personaId;

    @Column(
            name = "Nombre",
            nullable = false,
            length = 200
    )
    private String nombre;

    @Column(
            name = "Correo",
            nullable = false,
            length = 320
    )
    private String correo;

    @Column(
            name = "OrigenCodigo",
            nullable = false,
            length = 1
    )
    private Character origenCodigo;

    @Column(
            name = "TipoCuentaCodigo",
            nullable = false,
            length = 1
    )
    private Character tipoCuentaCodigo;

    @Column(
            name = "EstadoCuentaCodigo",
            nullable = false
    )
    private Integer estadoCuentaCodigo;

    @Column(
            name = "FechaInicioVigencia",
            nullable = false
    )
    private LocalDate fechaInicioVigencia;

    @Column(
            name = "FechaFinVigencia",
            nullable = false
    )
    private LocalDate fechaFinVigencia;

    @Column(
            name = "EstadoRegistro",
            nullable = false
    )
    private Boolean estadoRegistro;

    @Column(
            name = "FechaCreacion",
            nullable = false
    )
    private LocalDateTime fechaCreacion;

    @Column(
            name = "ActorCreacionId",
            nullable = false
    )
    private Long actorCreacionId;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "ActorModificacionId")
    private Long actorModificacionId;

    protected UsuarioJpaEntity() {
        // Constructor requerido por JPA.
    }

    public UsuarioJpaEntity(
            Long personaId,
            String nombre,
            String correo,
            Character origenCodigo,
            Character tipoCuentaCodigo,
            Integer estadoCuentaCodigo,
            LocalDate fechaInicioVigencia,
            LocalDate fechaFinVigencia,
            Boolean estadoRegistro,
            LocalDateTime fechaCreacion,
            Long actorCreacionId,
            LocalDateTime fechaModificacion,
            Long actorModificacionId
    ) {
        this.personaId = personaId;
        this.nombre = nombre;
        this.correo = correo;
        this.origenCodigo = origenCodigo;
        this.tipoCuentaCodigo = tipoCuentaCodigo;
        this.estadoCuentaCodigo = estadoCuentaCodigo;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
        this.estadoRegistro = estadoRegistro;
        this.fechaCreacion = fechaCreacion;
        this.actorCreacionId = actorCreacionId;
        this.fechaModificacion = fechaModificacion;
        this.actorModificacionId = actorModificacionId;
    }

    public UsuarioJpaEntity(
            Long usuarioId,
            Long personaId,
            String nombre,
            String correo,
            Character origenCodigo,
            Character tipoCuentaCodigo,
            Integer estadoCuentaCodigo,
            LocalDate fechaInicioVigencia,
            LocalDate fechaFinVigencia,
            Boolean estadoRegistro,
            LocalDateTime fechaCreacion,
            Long actorCreacionId,
            LocalDateTime fechaModificacion,
            Long actorModificacionId
    ) {
        this.usuarioId = usuarioId;
        this.personaId = personaId;
        this.nombre = nombre;
        this.correo = correo;
        this.origenCodigo = origenCodigo;
        this.tipoCuentaCodigo = tipoCuentaCodigo;
        this.estadoCuentaCodigo = estadoCuentaCodigo;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
        this.estadoRegistro = estadoRegistro;
        this.fechaCreacion = fechaCreacion;
        this.actorCreacionId = actorCreacionId;
        this.fechaModificacion = fechaModificacion;
        this.actorModificacionId = actorModificacionId;
    }

    public Long usuarioId() {
        return usuarioId;
    }

    public Long personaId() {
        return personaId;
    }

    public String nombre() {
        return nombre;
    }

    public String correo() {
        return correo;
    }

    public Character origenCodigo() {
        return origenCodigo;
    }

    public Integer estadoCuentaCodigo() {
        return estadoCuentaCodigo;
    }

    public Character tipoCuentaCodigo() {
        return tipoCuentaCodigo;
    }

    public LocalDate fechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public LocalDate fechaFinVigencia() {
        return fechaFinVigencia;
    }

    public Boolean estadoRegistro() {
        return estadoRegistro;
    }

    public LocalDateTime fechaCreacion() {
        return fechaCreacion;
    }

    public Long actorCreacionId() {
        return actorCreacionId;
    }

    public LocalDateTime fechaModificacion() {
        return fechaModificacion;
    }

    public Long actorModificacionId() {
        return actorModificacionId;
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioReadJpaEntity;

import java.util.Optional;

public interface UsuarioReadRepository
        extends JpaRepository<UsuarioReadJpaEntity, Long> {

    /*
     * ==========================================================
     * Consultar usuario por ID
     * ==========================================================
     */

    @Query("""
            select
                u.usuarioId as usuarioId,
                u.personaId as personaId,
                u.nombre as nombre,
                u.correo as correo,
                u.origenCodigo as origenCodigo,
                u.tipoCuentaCodigo as tipoCuentaCodigo,
                u.estadoCuentaCodigo as estadoCuentaCodigo,
                u.fechaInicioVigencia as fechaInicioVigencia,
                u.fechaFinVigencia as fechaFinVigencia,
                u.estadoRegistro as estadoRegistro,
                u.fechaCreacion as fechaCreacion,
                u.actorCreacionId as actorCreacionId,
                u.fechaModificacion as fechaModificacion,
                u.actorModificacionId as actorModificacionId
            from UsuarioReadJpaEntity u
            where u.usuarioId = :usuarioId
            """)
    Optional<UsuarioReadProjection> consultarPorId(
            @Param("usuarioId") Long usuarioId
    );

    /*
     * ==========================================================
     * Consultar usuarios
     * ==========================================================
     */

    @Query("""
            select
                u.usuarioId as usuarioId,
                u.personaId as personaId,
                u.nombre as nombre,
                u.correo as correo,
                u.origenCodigo as origenCodigo,
                u.tipoCuentaCodigo as tipoCuentaCodigo,
                u.estadoCuentaCodigo as estadoCuentaCodigo,
                u.fechaInicioVigencia as fechaInicioVigencia,
                u.fechaFinVigencia as fechaFinVigencia,
                u.estadoRegistro as estadoRegistro,
                u.fechaCreacion as fechaCreacion,
                u.actorCreacionId as actorCreacionId,
                u.fechaModificacion as fechaModificacion,
                u.actorModificacionId as actorModificacionId
            from UsuarioReadJpaEntity u
            where u.estadoRegistro = true
              and lower(u.nombre) like lower(concat('%', :nombre, '%'))
              and lower(u.correo) like lower(concat('%', :correo, '%'))
            """)
    Page<ConsultarUsuariosReadProjection> consultarUsuarios(
            @Param("nombre") String nombre,
            @Param("correo") String correo,
            Pageable pageable
    );
}
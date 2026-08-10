package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioJpaEntity;

public interface UsuarioWriteRepository
        extends JpaRepository<UsuarioJpaEntity, Long> {

    boolean existsByCorreo(
            String correo
    );

    boolean existsByCorreoAndUsuarioIdNot(
            String correo,
            Long usuarioId
    );

}
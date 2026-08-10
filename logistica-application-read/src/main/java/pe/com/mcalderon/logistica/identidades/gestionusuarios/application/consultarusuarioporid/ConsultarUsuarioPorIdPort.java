package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.util.Optional;

public interface ConsultarUsuarioPorIdPort {

    Optional<ConsultarUsuarioPorIdResult> consultarPorId(
            UsuarioId usuarioId
    );
}
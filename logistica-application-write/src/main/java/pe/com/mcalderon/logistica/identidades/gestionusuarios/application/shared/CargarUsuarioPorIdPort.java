package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.util.Optional;

public interface CargarUsuarioPorIdPort {

    Optional<Usuario> cargarPorId(UsuarioId usuarioId);
}
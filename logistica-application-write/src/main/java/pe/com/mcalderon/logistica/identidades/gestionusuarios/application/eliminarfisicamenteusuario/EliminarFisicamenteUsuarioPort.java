package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

public interface EliminarFisicamenteUsuarioPort {

    void eliminarFisicamente(
            UsuarioId usuarioId
    );
}
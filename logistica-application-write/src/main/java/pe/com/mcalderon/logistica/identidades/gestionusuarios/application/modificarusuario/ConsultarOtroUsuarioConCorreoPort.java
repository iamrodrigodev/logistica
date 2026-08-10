package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

public interface ConsultarOtroUsuarioConCorreoPort {

    boolean existeOtroUsuarioConCorreo(
            CorreoElectronico correo,
            UsuarioId usuarioIdExcluido
    );
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;

public interface ConsultarUsuarioPort {

    boolean existeUsuarioConCorreo(CorreoElectronico correo);
}
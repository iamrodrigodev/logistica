package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

public interface CrearUsuarioUseCase {

    CrearUsuarioResult crearUsuario(
            CrearUsuarioCommand command
    );
}
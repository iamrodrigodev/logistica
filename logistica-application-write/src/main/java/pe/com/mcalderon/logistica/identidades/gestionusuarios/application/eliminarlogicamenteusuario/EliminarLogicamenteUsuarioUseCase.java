package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario;

public interface EliminarLogicamenteUsuarioUseCase {

    void eliminarLogicamenteUsuario(
            EliminarLogicamenteUsuarioCommand command
    );
}
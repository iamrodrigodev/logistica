package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario;

public interface EliminarFisicamenteUsuarioUseCase {

    void eliminarFisicamenteUsuario(
            EliminarFisicamenteUsuarioCommand command
    );
}
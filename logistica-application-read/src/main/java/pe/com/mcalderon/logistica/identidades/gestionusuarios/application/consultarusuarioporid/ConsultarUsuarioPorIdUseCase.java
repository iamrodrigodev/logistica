package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

public interface ConsultarUsuarioPorIdUseCase {

    ConsultarUsuarioPorIdResult handle(
            ConsultarUsuarioPorIdQuery query
    );
}
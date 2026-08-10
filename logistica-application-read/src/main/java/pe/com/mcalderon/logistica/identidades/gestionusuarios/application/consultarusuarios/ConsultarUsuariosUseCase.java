package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

public interface ConsultarUsuariosUseCase {

    ConsultarUsuariosPageResult consultar(
            ConsultarUsuariosQuery query
    );
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

public interface ConsultarUsuariosPort {

    ConsultarUsuariosPageResult consultar(
            ConsultarUsuariosQuery query
    );
}
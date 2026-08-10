package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

import java.util.List;

public record ConsultarUsuariosPageResult(

        List<ConsultarUsuariosResult> usuarios,
        int pagina,
        int tamanio,
        long totalElementos,
        int totalPaginas

) {

    public ConsultarUsuariosPageResult {
        usuarios = List.copyOf(usuarios);
    }
}
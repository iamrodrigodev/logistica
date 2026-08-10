package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarios;

public record ConsultarUsuariosRequest(

        String nombre,
        String correo,
        Integer pagina,
        Integer tamanio

) {
}
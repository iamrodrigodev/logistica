package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

public record ConsultarUsuarioPorIdQuery(
        Long usuarioId
) {

    public ConsultarUsuarioPorIdQuery {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario no puede ser nulo"
            );
        }

        if (usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del usuario debe ser mayor que cero"
            );
        }
    }
}
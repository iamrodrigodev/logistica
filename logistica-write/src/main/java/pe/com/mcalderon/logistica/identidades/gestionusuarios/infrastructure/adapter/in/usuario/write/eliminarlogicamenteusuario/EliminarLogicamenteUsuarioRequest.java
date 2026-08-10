package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.eliminarlogicamenteusuario;

import jakarta.validation.constraints.NotNull;

public record EliminarLogicamenteUsuarioRequest(

        @NotNull(message = "El actor de modificación es obligatorio")
        Long actorModificacionId

) {
}
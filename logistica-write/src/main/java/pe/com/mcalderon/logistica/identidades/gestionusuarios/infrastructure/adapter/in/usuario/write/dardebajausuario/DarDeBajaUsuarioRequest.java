package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.dardebajausuario;

import jakarta.validation.constraints.NotNull;

public record DarDeBajaUsuarioRequest(

        @NotNull(message = "El actor de modificación es obligatorio")
        Long actorModificacionId

) {
}
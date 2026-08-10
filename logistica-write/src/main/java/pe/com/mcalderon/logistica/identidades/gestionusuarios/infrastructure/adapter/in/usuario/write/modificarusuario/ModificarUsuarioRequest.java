package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.modificarusuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ModificarUsuarioRequest(

        @NotBlank(
                message = "El nombre no puede estar vacío"
        )
        String nombre,

        @NotBlank(
                message = "El correo electrónico no puede estar vacío"
        )
        @Email(
                message = "El correo electrónico no tiene un formato válido"
        )
        String correo,

        @NotNull(
                message = "El origen del usuario es obligatorio"
        )
        Character origen,

        @NotNull(
                message = "El tipo de cuenta es obligatorio"
        )
        Character tipoCuenta,

        @NotNull(
                message = "La fecha inicial de vigencia es obligatoria"
        )
        LocalDate fechaInicioVigencia,

        @NotNull(
                message = "El actor de modificación es obligatorio"
        )
        @Positive(
                message = "El actor de modificación debe ser mayor que cero"
        )
        Long actorModificacionId

) {
}
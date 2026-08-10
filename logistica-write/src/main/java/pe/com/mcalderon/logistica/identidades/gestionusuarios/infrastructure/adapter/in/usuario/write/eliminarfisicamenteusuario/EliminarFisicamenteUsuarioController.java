package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.eliminarfisicamenteusuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class EliminarFisicamenteUsuarioController {

    // Use cases

    // Mappers

    private final EliminarFisicamenteUsuarioUseCase eliminarFisicamenteUsuarioUseCase;

    public EliminarFisicamenteUsuarioController(
            EliminarFisicamenteUsuarioUseCase eliminarFisicamenteUsuarioUseCase
    ) {
        // Use cases

        this.eliminarFisicamenteUsuarioUseCase =
                Objects.requireNonNull(
                        eliminarFisicamenteUsuarioUseCase,
                        "EliminarFisicamenteUsuarioUseCase no puede ser nulo"
                );

        // Mappers

    }

    //Endpoints
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> eliminarFisicamenteUsuario(
            @PathVariable Long usuarioId
    ) {
        EliminarFisicamenteUsuarioCommand command =
                new EliminarFisicamenteUsuarioCommand(
                        usuarioId
                );

        eliminarFisicamenteUsuarioUseCase.eliminarFisicamenteUsuario(
                command
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.eliminarlogicamenteusuario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class EliminarLogicamenteUsuarioController {

    // Use cases

    private final EliminarLogicamenteUsuarioUseCase eliminarLogicamenteUsuarioUseCase;

    // Mappers

    private final EliminarLogicamenteUsuarioRestMapper eliminarLogicamenteUsuarioRestMapper;

    public EliminarLogicamenteUsuarioController(
            EliminarLogicamenteUsuarioUseCase eliminarLogicamenteUsuarioUseCase,
            EliminarLogicamenteUsuarioRestMapper eliminarLogicamenteUsuarioRestMapper
    ) {
        // Use cases

        this.eliminarLogicamenteUsuarioUseCase =
                Objects.requireNonNull(
                        eliminarLogicamenteUsuarioUseCase,
                        "EliminarLogicamenteUsuarioUseCase no puede ser nulo"
                );

        // Mappers

        this.eliminarLogicamenteUsuarioRestMapper =
                Objects.requireNonNull(
                        eliminarLogicamenteUsuarioRestMapper,
                        "EliminarLogicamenteUsuarioRestMapper no puede ser nulo"
                );
    }

    //Endpoints

    @PatchMapping("/{usuarioId}/eliminar-logicamente")
    public ResponseEntity<Void> eliminarLogicamenteUsuario(
            @PathVariable Long usuarioId,
            @Valid
            @RequestBody EliminarLogicamenteUsuarioRequest request
    ) {
        EliminarLogicamenteUsuarioCommand command =
                eliminarLogicamenteUsuarioRestMapper.aCommand(
                        usuarioId,
                        request
                );

        eliminarLogicamenteUsuarioUseCase.eliminarLogicamenteUsuario(
                command
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
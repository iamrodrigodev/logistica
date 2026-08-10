package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.modificarusuario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class ModificarUsuarioController {

    // Use cases
    private final ModificarUsuarioUseCase modificarUsuarioUseCase;

    // Mappers

    private final ModificarUsuarioRestMapper modificarUsuarioRestMapper;

    public ModificarUsuarioController(
            ModificarUsuarioUseCase modificarUsuarioUseCase,
            ModificarUsuarioRestMapper modificarUsuarioRestMapper
    ) {
        // Use cases
        this.modificarUsuarioUseCase = Objects.requireNonNull(
                modificarUsuarioUseCase,
                "ModificarUsuarioUseCase no puede ser nulo"
        );

        // Mappers
        this.modificarUsuarioRestMapper = Objects.requireNonNull(
                modificarUsuarioRestMapper,
                "ModificarUsuarioRestMapper no puede ser nulo"
        );
    }

    //Endpoints
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> modificarUsuario(
            @PathVariable Long usuarioId,
            @Valid
            @RequestBody ModificarUsuarioRequest request
    ) {
        ModificarUsuarioCommand command =
                modificarUsuarioRestMapper.aCommand(
                        usuarioId,
                        request
                );

        modificarUsuarioUseCase.modificarUsuario(
                command
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
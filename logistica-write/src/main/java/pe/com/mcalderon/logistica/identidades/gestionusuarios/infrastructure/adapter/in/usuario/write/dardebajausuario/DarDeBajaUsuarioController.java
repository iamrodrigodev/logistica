package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.dardebajausuario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class DarDeBajaUsuarioController {

    // Use cases
    private final DarDeBajaUsuarioUseCase darDeBajaUsuarioUseCase;

    // Mappers
    private final DarDeBajaUsuarioRestMapper darDeBajaUsuarioRestMapper;

    public DarDeBajaUsuarioController(
            DarDeBajaUsuarioUseCase darDeBajaUsuarioUseCase,
            DarDeBajaUsuarioRestMapper darDeBajaUsuarioRestMapper
    ) {
        // Use cases
        this.darDeBajaUsuarioUseCase = Objects.requireNonNull(
                darDeBajaUsuarioUseCase,
                "DarDeBajaUsuarioUseCase no puede ser nulo"
        );

        // Mappers
        this.darDeBajaUsuarioRestMapper = Objects.requireNonNull(
                darDeBajaUsuarioRestMapper,
                "DarDeBajaUsuarioRestMapper no puede ser nulo"
        );
    }

    //Endpoints

    @PatchMapping("/{usuarioId}/dar-baja")
    public ResponseEntity<Void> darDeBajaUsuario(
            @PathVariable Long usuarioId,
            @Valid
            @RequestBody DarDeBajaUsuarioRequest request
    ) {
        DarDeBajaUsuarioCommand command =
                darDeBajaUsuarioRestMapper.aCommand(
                        usuarioId,
                        request
                );

        darDeBajaUsuarioUseCase.darDeBajaUsuario(
                command
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
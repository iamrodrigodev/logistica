package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class CrearUsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final CrearUsuarioRestMapper crearUsuarioRestMapper;

    public CrearUsuarioController(
            CrearUsuarioUseCase crearUsuarioUseCase,
            CrearUsuarioRestMapper crearUsuarioRestMapper
    ) {
        this.crearUsuarioUseCase = Objects.requireNonNull(
                crearUsuarioUseCase,
                "CrearUsuarioUseCase no puede ser nulo"
        );

        this.crearUsuarioRestMapper = Objects.requireNonNull(
                crearUsuarioRestMapper,
                "CrearUsuarioRestMapper no puede ser nulo"
        );
    }

    @PostMapping
    public ResponseEntity<CrearUsuarioResponse> crear(
            @Valid
            @RequestBody CrearUsuarioRequest request
    ) {
        CrearUsuarioCommand command =
                crearUsuarioRestMapper.aCommand(
                        request
                );

        CrearUsuarioResult result =
                crearUsuarioUseCase.crearUsuario(
                        command
                );

        CrearUsuarioResponse response =
                crearUsuarioRestMapper.aResponse(
                        result
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
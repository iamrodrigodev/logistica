package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarioporid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdQuery;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class ConsultarUsuarioPorIdController {

    // Use cases
    private final ConsultarUsuarioPorIdUseCase consultarUsuarioPorIdUseCase;

    // Mappers
    private final ConsultarUsuarioRestMapper consultarUsuarioRestMapper;

    public ConsultarUsuarioPorIdController(
            ConsultarUsuarioPorIdUseCase consultarUsuarioPorIdUseCase,
            ConsultarUsuarioRestMapper consultarUsuarioRestMapper
    ) {
        // Use cases

        this.consultarUsuarioPorIdUseCase  = Objects.requireNonNull(
                consultarUsuarioPorIdUseCase,
                "ConsultarUsuarioPorIdUseCase no puede ser nulo"
        );


        // Mappers

        this.consultarUsuarioRestMapper = Objects.requireNonNull(
                consultarUsuarioRestMapper,
                "ConsultarUsuarioRestMapper no puede ser nulo"
        );

    }

    //Endpoints

    @GetMapping("/{usuarioId}")
    public ResponseEntity<ConsultarUsuarioPorIdResponse> consultarPorId(
            @PathVariable Long usuarioId
    ) {
        ConsultarUsuarioPorIdQuery query =
                new ConsultarUsuarioPorIdQuery(
                        usuarioId
                );

        ConsultarUsuarioPorIdResult result =
                consultarUsuarioPorIdUseCase.handle(
                        query
                );

        ConsultarUsuarioPorIdResponse response =
                consultarUsuarioRestMapper.aResponse(
                        result
                );

        return ResponseEntity.ok(
                response
        );
    }

}
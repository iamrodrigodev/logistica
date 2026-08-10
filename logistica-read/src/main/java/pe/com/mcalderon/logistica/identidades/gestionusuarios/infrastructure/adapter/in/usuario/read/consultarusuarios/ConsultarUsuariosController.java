package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosPageResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosQuery;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
public final class ConsultarUsuariosController {

    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;
    private final ConsultarUsuariosRestMapper consultarUsuariosRestMapper;

    public ConsultarUsuariosController(
            ConsultarUsuariosUseCase consultarUsuariosUseCase,
            ConsultarUsuariosRestMapper consultarUsuariosRestMapper
    ) {

        this.consultarUsuariosUseCase = Objects.requireNonNull(
                consultarUsuariosUseCase,
                "ConsultarUsuariosUseCase no puede ser nulo"
        );

        this.consultarUsuariosRestMapper = Objects.requireNonNull(
                consultarUsuariosRestMapper,
                "ConsultarUsuariosRestMapper no puede ser nulo"
        );
    }

    @PostMapping("/consultar")
    public ResponseEntity<ConsultarUsuariosPageResult> consultar(
            @RequestBody ConsultarUsuariosRequest request
    ) {

        ConsultarUsuariosQuery query =
                consultarUsuariosRestMapper.aQuery(
                        request
                );

        ConsultarUsuariosPageResult result =
                consultarUsuariosUseCase.consultar(
                        query
                );

        return ResponseEntity.ok(
                result
        );
    }
}
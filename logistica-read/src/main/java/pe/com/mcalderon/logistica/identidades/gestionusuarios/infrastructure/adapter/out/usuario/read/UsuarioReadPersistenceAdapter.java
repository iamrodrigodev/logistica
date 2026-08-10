package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosPageResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosQuery;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class UsuarioReadPersistenceAdapter
        implements ConsultarUsuarioPorIdPort,
        ConsultarUsuariosPort {

    private final UsuarioReadRepository repository;
    private final UsuarioReadMapper mapper;

    public UsuarioReadPersistenceAdapter(
            UsuarioReadRepository repository,
            UsuarioReadMapper mapper
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "UsuarioReadRepository no puede ser nulo"
        );

        this.mapper = Objects.requireNonNull(
                mapper,
                "UsuarioReadMapper no puede ser nulo"
        );
    }

    @Override
    public Optional<ConsultarUsuarioPorIdResult> consultarPorId(
            UsuarioId usuarioId
    ) {
        Objects.requireNonNull(
                usuarioId,
                "UsuarioId no puede ser nulo"
        );

        return repository
                .consultarPorId(usuarioId.valor())
                .map(mapper::aResult);
    }

    @Override
    public ConsultarUsuariosPageResult consultar(
            ConsultarUsuariosQuery query
    ) {
        Objects.requireNonNull(
                query,
                "ConsultarUsuariosQuery no puede ser nulo"
        );

        Pageable pageable =
                PageRequest.of(
                        query.pagina(),
                        query.tamanio()
                );

        String nombre = normalizarFiltro(query.nombre());
        String correo = normalizarFiltro(query.correo());

        Page<ConsultarUsuariosReadProjection> page =
                repository.consultarUsuarios(
                        nombre,
                        correo,
                        pageable
                );

        List<ConsultarUsuariosResult> usuarios =
                page.getContent()
                        .stream()
                        .map(mapper::aConsultarUsuariosResult)
                        .toList();

        return new ConsultarUsuariosPageResult(
                usuarios,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private String normalizarFiltro(String filtro) {
        return filtro == null
                ? ""
                : filtro.trim();
    }
}
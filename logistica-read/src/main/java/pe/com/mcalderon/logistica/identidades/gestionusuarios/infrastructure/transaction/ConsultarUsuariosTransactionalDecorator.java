package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosQuery;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosPageResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosUseCase;

public class ConsultarUsuariosTransactionalDecorator
        implements ConsultarUsuariosUseCase {

    private final ConsultarUsuariosUseCase delegate;

    public ConsultarUsuariosTransactionalDecorator(
            ConsultarUsuariosUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultarUsuariosPageResult consultar(
            ConsultarUsuariosQuery query
    ) {
        return delegate.consultar(query);
    }
}
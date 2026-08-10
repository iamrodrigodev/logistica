package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdQuery;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdUseCase;

public class ConsultarUsuarioPorIdTransactionalDecorator
        implements ConsultarUsuarioPorIdUseCase {

    private final ConsultarUsuarioPorIdUseCase delegate;

    public ConsultarUsuarioPorIdTransactionalDecorator(
            ConsultarUsuarioPorIdUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultarUsuarioPorIdResult handle(
            ConsultarUsuarioPorIdQuery query
    ) {
        return delegate.handle(query);
    }
}
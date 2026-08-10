package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioUseCase;

public class DarDeBajaUsuarioTransactionalDecorator
        implements DarDeBajaUsuarioUseCase {

    private final DarDeBajaUsuarioUseCase delegate;

    public DarDeBajaUsuarioTransactionalDecorator(
            DarDeBajaUsuarioUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public void darDeBajaUsuario(
            DarDeBajaUsuarioCommand command
    ) {
        delegate.darDeBajaUsuario(command);
    }
}
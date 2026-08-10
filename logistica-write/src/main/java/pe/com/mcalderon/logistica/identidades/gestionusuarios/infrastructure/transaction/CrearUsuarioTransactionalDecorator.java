package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioUseCase;

public class CrearUsuarioTransactionalDecorator
        implements CrearUsuarioUseCase {

    private final CrearUsuarioUseCase delegate;

    public CrearUsuarioTransactionalDecorator(
            CrearUsuarioUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public CrearUsuarioResult crearUsuario(
            CrearUsuarioCommand command
    ) {
        return delegate.crearUsuario(command);
    }
}
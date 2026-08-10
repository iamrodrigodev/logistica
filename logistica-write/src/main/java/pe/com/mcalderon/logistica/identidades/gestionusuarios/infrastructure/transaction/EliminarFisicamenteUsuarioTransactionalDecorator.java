package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioUseCase;

public class EliminarFisicamenteUsuarioTransactionalDecorator
        implements EliminarFisicamenteUsuarioUseCase {

    private final EliminarFisicamenteUsuarioUseCase delegate;

    public EliminarFisicamenteUsuarioTransactionalDecorator(
            EliminarFisicamenteUsuarioUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public void eliminarFisicamenteUsuario(
            EliminarFisicamenteUsuarioCommand command
    ) {
        delegate.eliminarFisicamenteUsuario(command);
    }
}
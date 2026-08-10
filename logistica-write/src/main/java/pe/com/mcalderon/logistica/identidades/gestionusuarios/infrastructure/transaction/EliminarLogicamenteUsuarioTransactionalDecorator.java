package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioUseCase;

public class EliminarLogicamenteUsuarioTransactionalDecorator
        implements EliminarLogicamenteUsuarioUseCase {

    private final EliminarLogicamenteUsuarioUseCase delegate;

    public EliminarLogicamenteUsuarioTransactionalDecorator(
            EliminarLogicamenteUsuarioUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public void eliminarLogicamenteUsuario(
            EliminarLogicamenteUsuarioCommand command
    ) {
        delegate.eliminarLogicamenteUsuario(command);
    }
}
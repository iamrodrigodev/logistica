package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction;

import org.springframework.transaction.annotation.Transactional;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioUseCase;

public class ModificarUsuarioTransactionalDecorator
        implements ModificarUsuarioUseCase {

    private final ModificarUsuarioUseCase delegate;

    public ModificarUsuarioTransactionalDecorator(
            ModificarUsuarioUseCase delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public void modificarUsuario(ModificarUsuarioCommand command) {
        delegate.modificarUsuario(command);
    }
}
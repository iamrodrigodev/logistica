package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.config.read;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid.ConsultarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarioporid.ConsultarUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read.UsuarioReadPersistenceAdapter;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read.UsuarioReadMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read.UsuarioReadRepository;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios.ConsultarUsuariosPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.consultarusuarios.ConsultarUsuariosRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.ConsultarUsuarioPorIdTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.ConsultarUsuariosTransactionalDecorator;

@Configuration
public class GestionUsuariosReadConfig {

    /*
     * ==========================================================
     * Persistence
     * ==========================================================
     */

    @Bean
    public UsuarioReadMapper usuarioReadMapper() {
        return new UsuarioReadMapper();
    }

    @Bean
    public UsuarioReadPersistenceAdapter usuarioReadPersistenceAdapter(
            UsuarioReadRepository usuarioReadRepository,
            UsuarioReadMapper usuarioReadMapper
    ) {

        return new UsuarioReadPersistenceAdapter(
                usuarioReadRepository,
                usuarioReadMapper
        );
    }

    /*
     * ==========================================================
     * REST Mappers
     * ==========================================================
     */

    @Bean
    public ConsultarUsuarioRestMapper consultarUsuarioRestMapper() {

        return new ConsultarUsuarioRestMapper();
    }

    @Bean
    public ConsultarUsuariosRestMapper consultarUsuariosRestMapper() {

        return new ConsultarUsuariosRestMapper();

    }

    /*
     * ==========================================================
     * Query Handlers
     * ==========================================================
     */

    @Bean
    public ConsultarUsuarioPorIdUseCase consultarUsuarioPorIdUseCase(
            ConsultarUsuarioPorIdPort consultarUsuarioPorIdPort
    ) {

        ConsultarUsuarioPorIdHandler handler =
                new ConsultarUsuarioPorIdHandler(
                        consultarUsuarioPorIdPort
                );

        return new ConsultarUsuarioPorIdTransactionalDecorator(
                handler
        );
    }

    @Bean
    public ConsultarUsuariosUseCase consultarUsuariosUseCase(
            ConsultarUsuariosPort consultarUsuariosPort
    ) {

        ConsultarUsuariosHandler handler =
                new ConsultarUsuariosHandler(
                        consultarUsuariosPort
                );

        return new ConsultarUsuariosTransactionalDecorator(
                handler
        );
    }

}
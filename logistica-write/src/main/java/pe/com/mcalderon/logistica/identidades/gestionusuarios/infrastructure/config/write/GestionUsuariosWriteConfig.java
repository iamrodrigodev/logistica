package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.config.write;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.ConsultarPersonaPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.ConsultarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ConsultarOtroUsuarioConCorreoPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.CrearUsuarioTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario.DarDeBajaUsuarioUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.DarDeBajaUsuarioTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarfisicamenteusuario.EliminarFisicamenteUsuarioUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.EliminarFisicamenteUsuarioTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.EliminarLogicamenteUsuarioTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioHandler;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario.ModificarUsuarioUseCase;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.transaction.ModificarUsuarioTransactionalDecorator;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuarioEstandar;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write.UsuarioWritePersistenceAdapter;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write.UsuarioWriteMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write.UsuarioWriteRepository;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario.CrearUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.modificarusuario.ModificarUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.dardebajausuario.DarDeBajaUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.eliminarlogicamenteusuario.EliminarLogicamenteUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.persona.ConsultarPersonaAdapter;
import pe.com.mcalderon.logistica.personas.gestionpersonas.application.existepersona.ExistePersonaHandler;
import pe.com.mcalderon.logistica.personas.gestionpersonas.application.existepersona.ExistePersonaUseCase;
import pe.com.mcalderon.logistica.personas.gestionpersonas.application.port.out.read.ConsultarExistenciaPersonaPort;
import pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.read.PersonaReadPersistenceAdapter;
import pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.read.PersonaReadRepository;


import java.time.Clock;

@Configuration
public class GestionUsuariosWriteConfig {

    /*
     * ==========================================================
     * Shared Services
     * ==========================================================
     */

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public PoliticaVigenciaUsuario politicaVigenciaUsuario() {

        return new PoliticaVigenciaUsuarioEstandar();
    }

    /*
     * ==========================================================
     * Persistence
     * ==========================================================
     */

    @Bean
    public UsuarioWriteMapper usuarioWriteMapper() {

        return new UsuarioWriteMapper();
    }

    @Bean
    public UsuarioWritePersistenceAdapter usuarioWritePersistenceAdapter(
            UsuarioWriteRepository usuarioWriteRepository,
            UsuarioWriteMapper usuarioWriteMapper
    ) {

        return new UsuarioWritePersistenceAdapter(
                usuarioWriteRepository,
                usuarioWriteMapper
        );
    }

    @Bean
    public PersonaReadPersistenceAdapter personaReadPersistenceAdapter(
            PersonaReadRepository personaReadRepository
    ) {
        return new PersonaReadPersistenceAdapter(
                personaReadRepository
        );
    }

    @Bean
    public ExistePersonaHandler existePersonaHandler(
            ConsultarExistenciaPersonaPort consultarExistenciaPersonaPort
    ) {
        return new ExistePersonaHandler(
                consultarExistenciaPersonaPort
        );
    }

    @Bean
    public ConsultarPersonaAdapter consultarPersonaAdapter(
            ExistePersonaUseCase existePersonaUseCase
    ) {
        return new ConsultarPersonaAdapter(
                existePersonaUseCase
        );
    }


    /*
     * ==========================================================
     * Command Handlers
     * ==========================================================
     */

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(
            ConsultarUsuarioPort consultarUsuarioPort,
            GuardarUsuarioPort guardarUsuarioPort,
            ConsultarPersonaPort consultarPersonaPort,
            PoliticaVigenciaUsuario politicaVigenciaUsuario,
            Clock clock
    ) {

        CrearUsuarioHandler handler =
                new CrearUsuarioHandler(
                        consultarUsuarioPort,
                        guardarUsuarioPort,
                        consultarPersonaPort,
                        politicaVigenciaUsuario,
                        clock
                );

        return new CrearUsuarioTransactionalDecorator(
                handler
        );
    }

    @Bean
    public ModificarUsuarioUseCase modificarUsuarioUseCase(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            ConsultarOtroUsuarioConCorreoPort consultarOtroUsuarioConCorreoPort,
            GuardarUsuarioPort guardarUsuarioPort,
            PoliticaVigenciaUsuario politicaVigenciaUsuario,
            Clock clock
    ) {

        ModificarUsuarioHandler handler =
                new ModificarUsuarioHandler(
                        cargarUsuarioPorIdPort,
                        consultarOtroUsuarioConCorreoPort,
                        guardarUsuarioPort,
                        politicaVigenciaUsuario,
                        clock
                );

        return new ModificarUsuarioTransactionalDecorator(handler);
    }

    @Bean
    public DarDeBajaUsuarioUseCase darDeBajaUsuarioUseCase(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            GuardarUsuarioPort guardarUsuarioPort,
            Clock clock
    ) {

        DarDeBajaUsuarioHandler handler =
                new DarDeBajaUsuarioHandler(
                        cargarUsuarioPorIdPort,
                        guardarUsuarioPort,
                        clock
                );

        return new DarDeBajaUsuarioTransactionalDecorator(
                handler
        );
    }

    @Bean
    public EliminarLogicamenteUsuarioUseCase eliminarLogicamenteUsuarioUseCase(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            GuardarUsuarioPort guardarUsuarioPort,
            Clock clock
    ) {

        EliminarLogicamenteUsuarioHandler handler =
                new EliminarLogicamenteUsuarioHandler(
                        cargarUsuarioPorIdPort,
                        guardarUsuarioPort,
                        clock
                );

        return new EliminarLogicamenteUsuarioTransactionalDecorator(
                handler
        );
    }

    @Bean
    public EliminarFisicamenteUsuarioUseCase eliminarFisicamenteUsuarioUseCase(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            EliminarFisicamenteUsuarioPort eliminarFisicamenteUsuarioPort
    ) {

        EliminarFisicamenteUsuarioHandler handler =
                new EliminarFisicamenteUsuarioHandler(
                        cargarUsuarioPorIdPort,
                        eliminarFisicamenteUsuarioPort
                );

        return new EliminarFisicamenteUsuarioTransactionalDecorator(
                handler
        );
    }

    /*
     * ==========================================================
     * REST Mappers
     * ==========================================================
     */

    @Bean
    public CrearUsuarioRestMapper crearUsuarioRestMapper() {
        return new CrearUsuarioRestMapper();
    }

    @Bean
    public ModificarUsuarioRestMapper modificarUsuarioRestMapper() {
        return new ModificarUsuarioRestMapper();
    }

    @Bean
    public DarDeBajaUsuarioRestMapper darDeBajaUsuarioRestMapper() {
        return new DarDeBajaUsuarioRestMapper();
    }

    @Bean
    public EliminarLogicamenteUsuarioRestMapper eliminarLogicamenteUsuarioRestMapper() {
        return new EliminarLogicamenteUsuarioRestMapper();
    }

}
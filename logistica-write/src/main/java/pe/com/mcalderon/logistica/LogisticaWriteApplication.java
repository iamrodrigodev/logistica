package pe.com.mcalderon.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.config.write.GestionUsuariosWriteConfig;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioJpaEntity;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write.UsuarioWritePersistenceAdapter;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.write.UsuarioWriteRepository;
import pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.entity.PersonaJpaEntity;
import pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.read.PersonaReadRepository;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.WriteRestModule;

@SpringBootApplication
@ComponentScan(
        basePackageClasses = {
                GestionUsuariosWriteConfig.class,
                WriteRestModule.class,
                UsuarioWritePersistenceAdapter.class
        }
)
@EnableJpaRepositories(
        basePackageClasses = {
                UsuarioWriteRepository.class,
                PersonaReadRepository.class
        }
)
@EntityScan(
        basePackageClasses = {
                UsuarioJpaEntity.class,
                PersonaJpaEntity.class
        }
)
public class LogisticaWriteApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                LogisticaWriteApplication.class,
                args
        );

    }

}
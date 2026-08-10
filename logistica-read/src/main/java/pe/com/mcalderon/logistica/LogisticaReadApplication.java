package pe.com.mcalderon.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.ReadRestModule;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.config.read.GestionUsuariosReadConfig;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.entity.UsuarioReadJpaEntity;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read.UsuarioReadPersistenceAdapter;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.usuario.read.UsuarioReadRepository;

@SpringBootApplication
@ComponentScan(
        basePackageClasses = {
                GestionUsuariosReadConfig.class,
                ReadRestModule.class,
                UsuarioReadPersistenceAdapter.class
        }
)
@EnableJpaRepositories(
        basePackageClasses = {
                UsuarioReadRepository.class
        }
)
@EntityScan(
        basePackageClasses = {
                UsuarioReadJpaEntity.class
        }
)
public class LogisticaReadApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                LogisticaReadApplication.class,
                args
        );

    }

}
package pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.read;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.entity.PersonaJpaEntity;

public interface PersonaReadRepository
        extends JpaRepository<PersonaJpaEntity, Long> {
}
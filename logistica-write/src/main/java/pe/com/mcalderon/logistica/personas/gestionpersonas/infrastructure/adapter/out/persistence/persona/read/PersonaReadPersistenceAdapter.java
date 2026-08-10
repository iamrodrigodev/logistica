package pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.read;

import pe.com.mcalderon.logistica.personas.gestionpersonas.application.port.out.read.ConsultarExistenciaPersonaPort;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

import java.util.Objects;

public final class PersonaReadPersistenceAdapter
        implements ConsultarExistenciaPersonaPort {

    private final PersonaReadRepository repository;

    public PersonaReadPersistenceAdapter(
            PersonaReadRepository repository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "PersonaReadRepository no puede ser nulo"
        );
    }

    @Override
    public boolean existePersona(
            PersonaId personaId
    ) {
        Objects.requireNonNull(
                personaId,
                "PersonaId no puede ser nulo"
        );

        return repository.existsById(
                personaId.valor()
        );
    }
}
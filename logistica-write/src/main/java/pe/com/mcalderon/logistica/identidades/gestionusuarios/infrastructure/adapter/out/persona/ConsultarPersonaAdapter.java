package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.out.persona;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.ConsultarPersonaPort;
import pe.com.mcalderon.logistica.personas.gestionpersonas.application.existepersona.ExistePersonaUseCase;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

import java.util.Objects;

public final class ConsultarPersonaAdapter
        implements ConsultarPersonaPort {

    private final ExistePersonaUseCase existePersonaUseCase;

    public ConsultarPersonaAdapter(
            ExistePersonaUseCase existePersonaUseCase
    ) {
        this.existePersonaUseCase =
                Objects.requireNonNull(
                        existePersonaUseCase,
                        "ExistePersonaUseCase no puede ser nulo"
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

        return existePersonaUseCase
                .existePersona(personaId);
    }
}
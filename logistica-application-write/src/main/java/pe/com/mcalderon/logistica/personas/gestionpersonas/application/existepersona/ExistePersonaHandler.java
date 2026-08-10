package pe.com.mcalderon.logistica.personas.gestionpersonas.application.existepersona;

import pe.com.mcalderon.logistica.personas.gestionpersonas.application.port.out.read.ConsultarExistenciaPersonaPort;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

import java.util.Objects;

public final class ExistePersonaHandler
        implements ExistePersonaUseCase {

    private final ConsultarExistenciaPersonaPort consultarExistenciaPersonaPort;

    public ExistePersonaHandler(
            ConsultarExistenciaPersonaPort consultarExistenciaPersonaPort
    ) {
        this.consultarExistenciaPersonaPort =
                Objects.requireNonNull(
                        consultarExistenciaPersonaPort,
                        "ConsultarExistenciaPersonaPort no puede ser nulo"
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

        return consultarExistenciaPersonaPort
                .existePersona(personaId);
    }
}
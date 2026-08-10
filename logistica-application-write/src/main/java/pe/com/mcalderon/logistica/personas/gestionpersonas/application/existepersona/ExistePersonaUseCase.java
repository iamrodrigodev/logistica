package pe.com.mcalderon.logistica.personas.gestionpersonas.application.existepersona;

import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

public interface ExistePersonaUseCase {

    boolean existePersona(
            PersonaId personaId
    );
}
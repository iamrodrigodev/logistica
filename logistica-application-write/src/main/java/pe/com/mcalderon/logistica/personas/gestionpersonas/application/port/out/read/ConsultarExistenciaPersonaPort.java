package pe.com.mcalderon.logistica.personas.gestionpersonas.application.port.out.read;

import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

public interface ConsultarExistenciaPersonaPort {

    boolean existePersona(
            PersonaId personaId
    );
}
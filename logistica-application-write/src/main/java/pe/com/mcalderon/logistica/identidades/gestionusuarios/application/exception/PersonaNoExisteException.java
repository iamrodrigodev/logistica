package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception;

import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

public final class PersonaNoExisteException
        extends RuntimeException {

    public PersonaNoExisteException(PersonaId personaId) {
        super(
                "No existe una persona registrada con el identificador: "
                        + personaId
        );
    }
}

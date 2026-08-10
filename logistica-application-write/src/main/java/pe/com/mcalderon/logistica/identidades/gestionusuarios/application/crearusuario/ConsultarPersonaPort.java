package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;

public interface ConsultarPersonaPort {

    boolean existePersona(PersonaId personaId);
}
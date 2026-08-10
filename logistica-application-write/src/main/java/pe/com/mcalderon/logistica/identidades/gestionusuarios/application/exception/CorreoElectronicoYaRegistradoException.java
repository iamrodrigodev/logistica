package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;

public final class CorreoElectronicoYaRegistradoException
        extends RuntimeException {

    public CorreoElectronicoYaRegistradoException(
            CorreoElectronico correoElectronico
    ) {
        super(
                "Ya existe un usuario registrado con el correo electrónico: "
                        + correoElectronico
        );
    }
}
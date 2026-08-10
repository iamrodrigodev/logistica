package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.PersonaNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuario;
import pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model.PersonaId;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public final class CrearUsuarioHandler
        implements CrearUsuarioUseCase {

    private final ConsultarUsuarioPort consultarUsuarioPort;
    private final GuardarUsuarioPort guardarUsuarioPort;
    private final ConsultarPersonaPort consultarPersonaPort;
    private final PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private final Clock clock;

    public CrearUsuarioHandler(
            ConsultarUsuarioPort consultarUsuarioPort,
            GuardarUsuarioPort guardarUsuarioPort,
            ConsultarPersonaPort consultarPersonaPort,
            PoliticaVigenciaUsuario politicaVigenciaUsuario,
            Clock clock
    ) {
        this.consultarUsuarioPort = Objects.requireNonNull(
                consultarUsuarioPort,
                "ConsultarUsuarioPort no puede ser nulo"
        );

        this.guardarUsuarioPort = Objects.requireNonNull(
                guardarUsuarioPort,
                "GuardarUsuarioPort no puede ser nulo"
        );

        this.consultarPersonaPort = Objects.requireNonNull(
                consultarPersonaPort,
                "ConsultarPersonaPort no puede ser nulo"
        );

        this.politicaVigenciaUsuario = Objects.requireNonNull(
                politicaVigenciaUsuario,
                "PoliticaVigenciaUsuario no puede ser nula"
        );

        this.clock = Objects.requireNonNull(
                clock,
                "Clock no puede ser nulo"
        );
    }

    @Override
    public CrearUsuarioResult crearUsuario(
            CrearUsuarioCommand command
    ) {
        if (command == null) {
            throw new IllegalArgumentException(
                    "El comando para crear el usuario no puede ser nulo"
            );
        }

        /*
         * Traducción desde el lenguaje de Application
         * hacia el lenguaje del dominio.
         */
        PersonaId personaId = convertirPersonaId(
                command.personaId()
        );

        CorreoElectronico correoElectronico =
                new CorreoElectronico(command.correo());

        OrigenUsuario origenUsuario =
                OrigenUsuario.desdeCodigo(command.origen());

        TipoCuenta tipoCuenta =
                TipoCuenta.desdeCodigo(command.tipoCuenta());

        ActorAuditoriaId actorCreacionId =
                new ActorAuditoriaId(command.actorCreacionId());

        /*
         * Reglas que necesitan información externa.
         */
        validarExistenciaPersona(personaId);
        validarCorreoDisponible(correoElectronico);

        /*
         * La política del dominio calcula el periodo completo.
         */
        PeriodoVigencia periodoVigencia =
                politicaVigenciaUsuario.calcular(
                        command.fechaInicioVigencia(),
                        origenUsuario,
                        tipoCuenta
                );

        LocalDateTime fechaCreacion =
                LocalDateTime.now(clock);

        Usuario usuario = Usuario.crear(
                personaId,
                command.nombre(),
                correoElectronico,
                origenUsuario,
                tipoCuenta,
                periodoVigencia,
                fechaCreacion,
                actorCreacionId
        );

        UsuarioId usuarioId =
                guardarUsuarioPort.guardar(usuario);

        return new CrearUsuarioResult(
                usuarioId.valor()
        );
    }

    private PersonaId convertirPersonaId(
            Long personaId
    ) {
        return personaId == null
                ? null
                : new PersonaId(personaId);
    }

    private void validarExistenciaPersona(
            PersonaId personaId
    ) {
        if (personaId == null) {
            return;
        }

        if (!consultarPersonaPort.existePersona(personaId)) {
            throw new PersonaNoExisteException(personaId);
        }
    }

    private void validarCorreoDisponible(
            CorreoElectronico correoElectronico
    ) {
        if (consultarUsuarioPort.existeUsuarioConCorreo(
                correoElectronico
        )) {
            throw new CorreoElectronicoYaRegistradoException(
                    correoElectronico
            );
        }
    }
}
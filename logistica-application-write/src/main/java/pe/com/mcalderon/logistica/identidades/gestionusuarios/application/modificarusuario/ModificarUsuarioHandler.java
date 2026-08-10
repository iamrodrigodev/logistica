package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.modificarusuario;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.CargarUsuarioPorIdPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.shared.GuardarUsuarioPort;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.CorreoElectronico;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.Usuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service.PoliticaVigenciaUsuario;
import pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model.ActorAuditoriaId;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public class ModificarUsuarioHandler
        implements ModificarUsuarioUseCase {

    private final CargarUsuarioPorIdPort cargarUsuarioPorIdPort;
    private final ConsultarOtroUsuarioConCorreoPort consultarOtroUsuarioConCorreoPort;
    private final GuardarUsuarioPort guardarUsuarioPort;
    private final PoliticaVigenciaUsuario politicaVigenciaUsuario;
    private final Clock clock;

    public ModificarUsuarioHandler(
            CargarUsuarioPorIdPort cargarUsuarioPorIdPort,
            ConsultarOtroUsuarioConCorreoPort consultarOtroUsuarioConCorreoPort,
            GuardarUsuarioPort guardarUsuarioPort,
            PoliticaVigenciaUsuario politicaVigenciaUsuario,
            Clock clock
    ) {
        this.cargarUsuarioPorIdPort = Objects.requireNonNull(
                cargarUsuarioPorIdPort,
                "CargarUsuarioPorIdPort no puede ser nulo"
        );

        this.consultarOtroUsuarioConCorreoPort =
                Objects.requireNonNull(
                        consultarOtroUsuarioConCorreoPort,
                        "ConsultarOtroUsuarioConCorreoPort "
                                + "no puede ser nulo"
                );

        this.guardarUsuarioPort = Objects.requireNonNull(
                guardarUsuarioPort,
                "GuardarUsuarioPort no puede ser nulo"
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
    public void modificarUsuario(
            ModificarUsuarioCommand command
    ) {
        if (command == null) {
            throw new IllegalArgumentException(
                    "El comando para modificar el usuario "
                            + "no puede ser nulo"
            );
        }

        /*
         * Traducción desde Application hacia el lenguaje del dominio.
         */
        UsuarioId usuarioId =
                new UsuarioId(command.usuarioId());

        CorreoElectronico correoElectronico =
                new CorreoElectronico(command.correo());

        OrigenUsuario origenUsuario =
                OrigenUsuario.desdeCodigo(command.origen());

        TipoCuenta tipoCuenta =
                TipoCuenta.desdeCodigo(command.tipoCuenta());

        ActorAuditoriaId actorModificacionId =
                new ActorAuditoriaId(
                        command.actorModificacionId()
                );

        /*
         * Recuperación del agregado completo.
         */
        Usuario usuario = cargarUsuarioPorIdPort
                .cargarPorId(usuarioId)
                .orElseThrow(
                        () -> new UsuarioNoExisteException(
                                usuarioId
                        )
                );

        /*
         * Regla externa: el nuevo correo no debe pertenecer
         * a otro usuario.
         */
        if (consultarOtroUsuarioConCorreoPort
                .existeOtroUsuarioConCorreo(
                        correoElectronico,
                        usuarioId
                )) {
            throw new CorreoElectronicoYaRegistradoException(
                    correoElectronico
            );
        }

        /*
         * El periodo completo es calculado por la política
         * del dominio.
         */
        PeriodoVigencia periodoVigencia =
                politicaVigenciaUsuario.calcular(
                        command.fechaInicioVigencia(),
                        origenUsuario,
                        tipoCuenta
                );

        LocalDateTime fechaModificacion =
                LocalDateTime.now(clock);

        /*
         * Application coordina.
         * El agregado decide y modifica su propio estado.
         */
        usuario.modificar(
                command.nombre(),
                correoElectronico,
                origenUsuario,
                tipoCuenta,
                periodoVigencia,
                fechaModificacion,
                actorModificacionId
        );

        guardarUsuarioPort.guardar(usuario);
    }
}
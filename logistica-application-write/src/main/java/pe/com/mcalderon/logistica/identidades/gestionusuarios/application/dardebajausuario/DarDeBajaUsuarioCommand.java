package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.dardebajausuario;

public record DarDeBajaUsuarioCommand(
        Long usuarioId,
        Long actorModificacionId
) {
}
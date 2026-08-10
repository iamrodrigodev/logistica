package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.eliminarlogicamenteusuario;

public record EliminarLogicamenteUsuarioCommand(
        Long usuarioId,
        Long actorModificacionId
) {
}
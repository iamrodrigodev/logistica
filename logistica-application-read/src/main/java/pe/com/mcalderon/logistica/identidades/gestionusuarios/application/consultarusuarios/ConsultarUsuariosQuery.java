package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

public record ConsultarUsuariosQuery(

        String nombre,
        String correo,
        int pagina,
        int tamanio

) {

    private static final int TAMANIO_MAXIMO = 100;

    public ConsultarUsuariosQuery {

        nombre = normalizar(nombre);
        correo = normalizar(correo);

        if (pagina < 0) {
            throw new IllegalArgumentException(
                    "La página no puede ser menor que cero"
            );
        }

        if (tamanio <= 0) {
            throw new IllegalArgumentException(
                    "El tamaño de página debe ser mayor que cero"
            );
        }

        if (tamanio > TAMANIO_MAXIMO) {
            throw new IllegalArgumentException(
                    "El tamaño de página no puede ser mayor que "
                            + TAMANIO_MAXIMO
            );
        }
    }

    private static String normalizar(
            String valor
    ) {

        if (valor == null) {
            return null;
        }

        String valorNormalizado =
                valor.trim();

        return valorNormalizado.isEmpty()
                ? null
                : valorNormalizado;
    }
}
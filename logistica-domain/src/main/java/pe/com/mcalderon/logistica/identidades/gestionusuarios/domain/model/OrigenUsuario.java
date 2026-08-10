package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

public enum OrigenUsuario {

    INTERNO('I'),
    EXTERNO('E');

    private final char codigo;

    OrigenUsuario(char codigo) {
        this.codigo = codigo;
    }

    public char codigo() {
        return codigo;
    }

    public static OrigenUsuario desdeCodigo(char codigo) {
        char codigoNormalizado = Character.toUpperCase(codigo);

        for (OrigenUsuario origen : values()) {
            if (origen.codigo == codigoNormalizado) {
                return origen;
            }
        }

        throw new IllegalArgumentException(
                "Código de origen de usuario no válido: " + codigo
        );
    }
}
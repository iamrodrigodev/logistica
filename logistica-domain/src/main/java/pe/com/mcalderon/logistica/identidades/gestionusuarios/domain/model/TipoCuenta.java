package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

public enum TipoCuenta {

    PERSONAL('P'),
    ADMINISTRACION('A'),
    SERVICIO('S'),
    OTRO('O');

    private final char codigo;

    TipoCuenta(char codigo) {
        this.codigo = codigo;
    }

    public char codigo() {
        return codigo;
    }

    public static TipoCuenta desdeCodigo(char codigo) {
        char codigoNormalizado = Character.toUpperCase(codigo);

        for (TipoCuenta tipo : values()) {
            if (tipo.codigo == codigoNormalizado) {
                return tipo;
            }
        }

        throw new IllegalArgumentException(
                "Código de tipo de cuenta no válido: " + codigo
        );
    }
}
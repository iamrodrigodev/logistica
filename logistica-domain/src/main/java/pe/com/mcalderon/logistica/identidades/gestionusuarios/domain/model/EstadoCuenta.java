package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

public enum EstadoCuenta {
    ACTIVACION_PENDIENTE(1),
    ACTIVO(2),
    INACTIVO(3),
    SUSPENDIDO(4),
    CADUCADO(5),
    BAJA(6);

    private final int codigo;

    EstadoCuenta(int codigo) {
        this.codigo = codigo;
    }

    public int codigo() {
        return codigo;
    }

    public static EstadoCuenta desdeCodigo(int codigo) {
        for (EstadoCuenta estado : values()) {
            if (estado.codigo == codigo) {
                return estado;
            }
        }

        throw new IllegalArgumentException(
                "Código de estado de cuenta no válido: " + codigo
        );
    }
}
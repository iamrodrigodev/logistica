package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import java.util.Objects;

public final class UsuarioId {

    private final Long valor;

    public UsuarioId(Long valor) {
        if (valor == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario no puede ser nulo"
            );
        }

        if (valor <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del usuario debe ser mayor que cero"
            );
        }

        this.valor = valor;
    }

    public Long valor() {
        return valor;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof UsuarioId usuarioId)) {
            return false;
        }

        return Objects.equals(valor, usuarioId.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
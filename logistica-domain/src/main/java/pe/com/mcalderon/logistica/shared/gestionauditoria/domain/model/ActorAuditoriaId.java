package pe.com.mcalderon.logistica.shared.gestionauditoria.domain.model;

import java.util.Objects;

public final class ActorAuditoriaId {

    private final Long valor;

    public ActorAuditoriaId(Long valor) {
        if (valor == null) {
            throw new IllegalArgumentException(
                    "El identificador del actor de auditoría no puede ser nulo"
            );
        }

        if (valor <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del actor de auditoría debe ser mayor que cero"
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

        if (!(objeto instanceof ActorAuditoriaId actorAuditoriaId)) {
            return false;
        }

        return Objects.equals(valor, actorAuditoriaId.valor);
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

package pe.com.mcalderon.logistica.personas.gestionpersonas.domain.model;

import java.util.Objects;

public final class PersonaId {

    private final Long valor;

    public PersonaId(Long valor) {

        if (valor == null) {
            throw new IllegalArgumentException(
                    "El identificador de la persona no puede ser nulo"
            );
        }

        if (valor <= 0) {
            throw new IllegalArgumentException(
                    "El identificador de la persona debe ser mayor que cero"
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

        if (!(objeto instanceof PersonaId personaId)) {
            return false;
        }

        return Objects.equals(valor, personaId.valor);
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
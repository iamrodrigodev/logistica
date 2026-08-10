package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public final class CorreoElectronico {

    private static final Pattern FORMATO_VALIDO =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final String valor;

    public CorreoElectronico(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException(
                    "El correo electrónico no puede ser nulo"
            );
        }

        String valorNormalizado = valor
                .trim()
                .toLowerCase(Locale.ROOT);

        if (valorNormalizado.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo electrónico no puede estar vacío"
            );
        }

        if (!FORMATO_VALIDO.matcher(valorNormalizado).matches()) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido"
            );
        }

        this.valor = valorNormalizado;
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof CorreoElectronico correoElectronico)) {
            return false;
        }

        return Objects.equals(valor, correoElectronico.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
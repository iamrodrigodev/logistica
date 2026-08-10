package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public final class PeriodoVigencia {

    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;

    public PeriodoVigencia(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio de vigencia no puede ser nula"
            );
        }

        if (fechaFin == null) {
            throw new IllegalArgumentException(
                    "La fecha de fin de vigencia no puede ser nula"
            );
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha de fin de vigencia no puede ser anterior "
                            + "a la fecha de inicio"
            );
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public boolean estaVigente(LocalDate fechaEvaluacion) {
        if (fechaEvaluacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de evaluación no puede ser nula"
            );
        }

        return !fechaEvaluacion.isBefore(fechaInicio)
                && !fechaEvaluacion.isAfter(fechaFin);
    }

    public LocalDate fechaInicio() {
        return fechaInicio;
    }

    public LocalDate fechaFin() {
        return fechaFin;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof PeriodoVigencia periodoVigencia)) {
            return false;
        }

        return Objects.equals(fechaInicio, periodoVigencia.fechaInicio)
                && Objects.equals(fechaFin, periodoVigencia.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, fechaFin);
    }

    @Override
    public String toString() {
        return fechaInicio + " - " + fechaFin;
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PeriodoVigenciaTest {

    @Test
    void debeCrearUnPeriodoValido() {
        LocalDate fechaInicio = LocalDate.of(2026, 1, 1);
        LocalDate fechaFin = LocalDate.of(2026, 12, 31);

        PeriodoVigencia periodo =
                new PeriodoVigencia(fechaInicio, fechaFin);

        assertEquals(fechaInicio, periodo.fechaInicio());
        assertEquals(fechaFin, periodo.fechaFin());
    }

    @Test
    void debeConsiderarVigenteLaFechaInicial() {
        LocalDate fechaInicio = LocalDate.of(2026, 1, 1);
        LocalDate fechaFin = LocalDate.of(2026, 12, 31);

        PeriodoVigencia periodo =
                new PeriodoVigencia(fechaInicio, fechaFin);

        assertTrue(periodo.estaVigente(fechaInicio));
    }

    @Test
    void debeConsiderarVigenteLaFechaFinal() {
        LocalDate fechaInicio = LocalDate.of(2026, 1, 1);
        LocalDate fechaFin = LocalDate.of(2026, 12, 31);

        PeriodoVigencia periodo =
                new PeriodoVigencia(fechaInicio, fechaFin);

        assertTrue(periodo.estaVigente(fechaFin));
    }

    @Test
    void debeConsiderarVigenteUnaFechaIntermedia() {
        PeriodoVigencia periodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        assertTrue(
                periodo.estaVigente(
                        LocalDate.of(2026, 7, 15)
                )
        );
    }

    @Test
    void noDebeConsiderarVigenteUnaFechaAnterior() {
        PeriodoVigencia periodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        assertFalse(
                periodo.estaVigente(
                        LocalDate.of(2025, 12, 31)
                )
        );
    }

    @Test
    void noDebeConsiderarVigenteUnaFechaPosterior() {
        PeriodoVigencia periodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        assertFalse(
                periodo.estaVigente(
                        LocalDate.of(2027, 1, 1)
                )
        );
    }

    @Test
    void noDebePermitirFechaInicialNula() {
        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new PeriodoVigencia(
                                null,
                                LocalDate.of(2026, 12, 31)
                        )
                );

        assertEquals(
                "La fecha de inicio de vigencia no puede ser nula",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirFechaFinalNula() {
        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new PeriodoVigencia(
                                LocalDate.of(2026, 1, 1),
                                null
                        )
                );

        assertEquals(
                "La fecha de fin de vigencia no puede ser nula",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirFechaFinalAnteriorALaInicial() {
        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new PeriodoVigencia(
                                LocalDate.of(2026, 12, 31),
                                LocalDate.of(2026, 1, 1)
                        )
                );

        assertEquals(
                "La fecha de fin de vigencia no puede ser anterior "
                        + "a la fecha de inicio",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebeEvaluarVigenciaConFechaNula() {
        PeriodoVigencia periodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> periodo.estaVigente(null)
        );
    }

    @Test
    void dosPeriodosConLasMismasFechasDebenSerIguales() {
        PeriodoVigencia primerPeriodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        PeriodoVigencia segundoPeriodo =
                new PeriodoVigencia(
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31)
                );

        assertEquals(primerPeriodo, segundoPeriodo);
        assertEquals(
                primerPeriodo.hashCode(),
                segundoPeriodo.hashCode()
        );
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PoliticaVigenciaUsuarioEstandarTest {

    private final PoliticaVigenciaUsuario politica =
            new PoliticaVigenciaUsuarioEstandar();

    @ParameterizedTest
    @MethodSource("casosDeVigencia")
    void debeCalcularLaVigenciaSegunOrigenYTipoDeCuenta(
            OrigenUsuario origenUsuario,
            TipoCuenta tipoCuenta,
            int duracionDias
    ) {
        LocalDate fechaInicio = LocalDate.of(2026, 1, 1);

        PeriodoVigencia periodo =
                politica.calcular(
                        fechaInicio,
                        origenUsuario,
                        tipoCuenta
                );

        LocalDate fechaFinEsperada =
                fechaInicio.plusDays(duracionDias - 1L);

        assertEquals(
                fechaInicio,
                periodo.fechaInicio()
        );

        assertEquals(
                fechaFinEsperada,
                periodo.fechaFin()
        );
    }

    private static Stream<Arguments> casosDeVigencia() {
        return Stream.of(
                Arguments.of(
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        365
                ),
                Arguments.of(
                        OrigenUsuario.INTERNO,
                        TipoCuenta.ADMINISTRACION,
                        365
                ),
                Arguments.of(
                        OrigenUsuario.INTERNO,
                        TipoCuenta.SERVICIO,
                        365
                ),
                Arguments.of(
                        OrigenUsuario.INTERNO,
                        TipoCuenta.OTRO,
                        30
                ),
                Arguments.of(
                        OrigenUsuario.EXTERNO,
                        TipoCuenta.PERSONAL,
                        90
                ),
                Arguments.of(
                        OrigenUsuario.EXTERNO,
                        TipoCuenta.ADMINISTRACION,
                        10
                ),
                Arguments.of(
                        OrigenUsuario.EXTERNO,
                        TipoCuenta.SERVICIO,
                        60
                ),
                Arguments.of(
                        OrigenUsuario.EXTERNO,
                        TipoCuenta.OTRO,
                        10
                )
        );
    }

    @Test
    void noDebeCalcularSinFechaDeInicio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> politica.calcular(
                        null,
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL
                )
        );
    }

    @Test
    void noDebeCalcularSinOrigenDeUsuario() {
        assertThrows(
                IllegalArgumentException.class,
                () -> politica.calcular(
                        LocalDate.of(2026, 1, 1),
                        null,
                        TipoCuenta.PERSONAL
                )
        );
    }

    @Test
    void noDebeCalcularSinTipoDeCuenta() {
        assertThrows(
                IllegalArgumentException.class,
                () -> politica.calcular(
                        LocalDate.of(2026, 1, 1),
                        OrigenUsuario.INTERNO,
                        null
                )
        );
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CorreoElectronicoTest {

    @Test
    void debeCrearUnCorreoConFormatoValido() {
        CorreoElectronico correo =
                new CorreoElectronico(
                        "miguel@empresa.com"
                );

        assertEquals(
                "miguel@empresa.com",
                correo.valor()
        );
    }

    @Test
    void debeNormalizarEspaciosYMayusculas() {
        CorreoElectronico correo =
                new CorreoElectronico(
                        "  MIGUEL@EMPRESA.COM  "
                );

        assertEquals(
                "miguel@empresa.com",
                correo.valor()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "     "
    })
    void noDebePermitirCorreoNuloOVacio(String valor) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CorreoElectronico(valor)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "miguel",
            "miguel@",
            "@empresa.com",
            "miguel empresa@empresa.com",
            "miguel@empresa",
            "miguel@@empresa.com"
    })
    void noDebePermitirFormatosInvalidos(String valor) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CorreoElectronico(valor)
        );
    }

    @Test
    void dosCorreosNormalizadosIgualesDebenSerIguales() {
        CorreoElectronico primerCorreo =
                new CorreoElectronico(
                        "MIGUEL@EMPRESA.COM"
                );

        CorreoElectronico segundoCorreo =
                new CorreoElectronico(
                        " miguel@empresa.com "
                );

        assertEquals(primerCorreo, segundoCorreo);
        assertEquals(
                primerCorreo.hashCode(),
                segundoCorreo.hashCode()
        );
    }

    @Test
    void dosCorreosDiferentesNoDebenSerIguales() {
        CorreoElectronico primerCorreo =
                new CorreoElectronico(
                        "miguel@empresa.com"
                );

        CorreoElectronico segundoCorreo =
                new CorreoElectronico(
                        "gabriel@empresa.com"
                );

        assertNotEquals(primerCorreo, segundoCorreo);
    }

    @Test
    void toStringDebeDevolverElCorreoNormalizado() {
        CorreoElectronico correo =
                new CorreoElectronico(
                        " MIGUEL@EMPRESA.COM "
                );

        assertEquals(
                "miguel@empresa.com",
                correo.toString()
        );
    }
}
package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultarUsuariosQueryTest {

    @Test
    void debeCrearQueryConValoresValidos() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        "Miguel",
                        "miguel@empresa.com",
                        0,
                        20
                );

        assertEquals(
                "Miguel",
                query.nombre()
        );

        assertEquals(
                "miguel@empresa.com",
                query.correo()
        );

        assertEquals(
                0,
                query.pagina()
        );

        assertEquals(
                20,
                query.tamanio()
        );
    }

    @Test
    void debeEliminarEspaciosAlInicioYAlFinalDelNombre() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        "   Miguel Calderón   ",
                        null,
                        0,
                        20
                );

        assertEquals(
                "Miguel Calderón",
                query.nombre()
        );
    }

    @Test
    void debeEliminarEspaciosAlInicioYAlFinalDelCorreo() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        null,
                        "   miguel@empresa.com   ",
                        0,
                        20
                );

        assertEquals(
                "miguel@empresa.com",
                query.correo()
        );
    }

    @Test
    void debeConvertirNombreVacioEnNulo() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        "   ",
                        null,
                        0,
                        20
                );

        assertNull(
                query.nombre()
        );
    }

    @Test
    void debeConvertirCorreoVacioEnNulo() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        null,
                        "   ",
                        0,
                        20
                );

        assertNull(
                query.correo()
        );
    }

    @Test
    void debeAceptarFiltrosNulos() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        null,
                        null,
                        0,
                        20
                );

        assertNull(
                query.nombre()
        );

        assertNull(
                query.correo()
        );
    }

    @Test
    void debeRechazarPaginaNegativa() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuariosQuery(
                                null,
                                null,
                                -1,
                                20
                        )
                );

        assertEquals(
                "La página no puede ser menor que cero",
                exception.getMessage()
        );
    }

    @Test
    void debeRechazarTamanioCero() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuariosQuery(
                                null,
                                null,
                                0,
                                0
                        )
                );

        assertEquals(
                "El tamaño de página debe ser mayor que cero",
                exception.getMessage()
        );
    }

    @Test
    void debeRechazarTamanioNegativo() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuariosQuery(
                                null,
                                null,
                                0,
                                -1
                        )
                );

        assertEquals(
                "El tamaño de página debe ser mayor que cero",
                exception.getMessage()
        );
    }

    @Test
    void debeAceptarTamanioMaximoPermitido() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        null,
                        null,
                        0,
                        100
                );

        assertEquals(
                100,
                query.tamanio()
        );
    }

    @Test
    void debeRechazarTamanioMayorAlMaximoPermitido() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuariosQuery(
                                null,
                                null,
                                0,
                                101
                        )
                );

        assertEquals(
                "El tamaño de página no puede ser mayor que 100",
                exception.getMessage()
        );
    }
}
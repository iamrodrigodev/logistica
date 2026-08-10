package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultarUsuarioPorIdQueryTest {

    @Test
    void debeCrearQueryConUsuarioIdValido() {

        ConsultarUsuarioPorIdQuery query =
                assertDoesNotThrow(
                        () -> new ConsultarUsuarioPorIdQuery(100L)
                );

        assertEquals(
                100L,
                query.usuarioId()
        );
    }

    @Test
    void debeRechazarUsuarioIdNulo() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuarioPorIdQuery(null)
                );

        assertEquals(
                "El identificador del usuario no puede ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void debeRechazarUsuarioIdCero() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuarioPorIdQuery(0L)
                );

        assertEquals(
                "El identificador del usuario debe ser mayor que cero",
                exception.getMessage()
        );
    }

    @Test
    void debeRechazarUsuarioIdNegativo() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ConsultarUsuarioPorIdQuery(-1L)
                );

        assertEquals(
                "El identificador del usuario debe ser mayor que cero",
                exception.getMessage()
        );
    }
}
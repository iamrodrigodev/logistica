package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsultarUsuariosHandlerTest {

    private ConsultarUsuariosPort consultarUsuariosPort;
    private ConsultarUsuariosHandler handler;

    @BeforeEach
    void preparar() {

        consultarUsuariosPort =
                mock(ConsultarUsuariosPort.class);

        handler =
                new ConsultarUsuariosHandler(
                        consultarUsuariosPort
                );
    }

    @Test
    void debeConsultarUsuariosDelegandoAlPuertoDeLectura() {

        ConsultarUsuariosQuery query =
                new ConsultarUsuariosQuery(
                        "Miguel",
                        "miguel@empresa.com",
                        0,
                        20
                );

        ConsultarUsuariosPageResult resultadoEsperado =
                new ConsultarUsuariosPageResult(
                        List.of(),
                        0,
                        20,
                        0L,
                        0
                );

        when(
                consultarUsuariosPort.consultar(query)
        ).thenReturn(
                resultadoEsperado
        );

        ConsultarUsuariosPageResult resultado =
                handler.consultar(query);

        assertSame(
                resultadoEsperado,
                resultado
        );

        verify(
                consultarUsuariosPort
        ).consultar(query);
    }

    @Test
    void debeRechazarPuertoNulo() {

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> new ConsultarUsuariosHandler(null)
                );

        assertEquals(
                "ConsultarUsuariosPort no puede ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void debeRechazarQueryNulo() {

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> handler.consultar(null)
                );

        assertEquals(
                "ConsultarUsuariosQuery no puede ser nulo",
                exception.getMessage()
        );
    }
}
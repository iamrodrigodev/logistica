package pe.com.mcalderon.logistica.identidades.gestionusuarios.application.consultarusuarioporid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.EstadoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.UsuarioId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsultarUsuarioPorIdHandlerTest {

    private ConsultarUsuarioPorIdPort consultarUsuarioPorIdPort;
    private ConsultarUsuarioPorIdHandler handler;

    @BeforeEach
    void preparar() {

        consultarUsuarioPorIdPort =
                mock(ConsultarUsuarioPorIdPort.class);

        handler =
                new ConsultarUsuarioPorIdHandler(
                        consultarUsuarioPorIdPort
                );
    }

    @Test
    void debeConsultarUsuarioPorId() {

        ConsultarUsuarioPorIdQuery query =
                new ConsultarUsuarioPorIdQuery(
                        100L
                );

        ConsultarUsuarioPorIdResult resultadoEsperado =
                new ConsultarUsuarioPorIdResult(
                        100L,
                        10L,
                        "Miguel Calderón",
                        "miguel@empresa.com",
                        OrigenUsuario.INTERNO,
                        TipoCuenta.PERSONAL,
                        EstadoCuenta.ACTIVO,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31),
                        true,
                        LocalDateTime.of(
                                2026,
                                1,
                                1,
                                10,
                                0
                        ),
                        1L,
                        null,
                        null
                );

        when(
                consultarUsuarioPorIdPort.consultarPorId(
                        new UsuarioId(100L)
                )
        ).thenReturn(
                Optional.of(resultadoEsperado)
        );

        ConsultarUsuarioPorIdResult resultado =
                handler.handle(query);

        assertEquals(
                resultadoEsperado,
                resultado
        );

        verify(
                consultarUsuarioPorIdPort
        ).consultarPorId(
                new UsuarioId(100L)
        );
    }

    @Test
    void debeLanzarExcepcionCuandoUsuarioNoExiste() {

        ConsultarUsuarioPorIdQuery query =
                new ConsultarUsuarioPorIdQuery(
                        999L
                );

        when(
                consultarUsuarioPorIdPort.consultarPorId(
                        any(UsuarioId.class)
                )
        ).thenReturn(
                Optional.empty()
        );

        assertThrows(
                UsuarioNoExisteException.class,
                () -> handler.handle(query)
        );
    }

    @Test
    void debeRechazarQueryNulo() {

        assertThrows(
                NullPointerException.class,
                () -> handler.handle(null)
        );
    }
}
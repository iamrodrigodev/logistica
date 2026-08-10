package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.mapper;

import org.junit.jupiter.api.Test;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioCommand;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.crearusuario.CrearUsuarioResult;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario.CrearUsuarioRestMapper;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario.CrearUsuarioRequest;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.crearusuario.CrearUsuarioResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CrearUsuarioRestMapperTest {

    private final CrearUsuarioRestMapper mapper =
            new CrearUsuarioRestMapper();

    @Test
    void debeConvertirRequestACommand() {
        CrearUsuarioRequest request =
                new CrearUsuarioRequest(
                        10L,
                        "Miguel Calderón",
                        "miguel@empresa.com",
                        'I',
                        'P',
                        LocalDate.of(2026, 8, 1),
                        1L
                );

        CrearUsuarioCommand command =
                mapper.aCommand(request);

        assertEquals(
                request.personaId(),
                command.personaId()
        );

        assertEquals(
                request.nombre(),
                command.nombre()
        );

        assertEquals(
                request.correo(),
                command.correo()
        );

        assertEquals(
                request.origen().charValue(),
                command.origen()
        );

        assertEquals(
                request.tipoCuenta().charValue(),
                command.tipoCuenta()
        );

        assertEquals(
                request.fechaInicioVigencia(),
                command.fechaInicioVigencia()
        );

        assertEquals(
                request.actorCreacionId(),
                command.actorCreacionId()
        );
    }

    @Test
    void debeConvertirUsuarioResultAResponse() {

        CrearUsuarioResult result =
                new CrearUsuarioResult(100L);

        CrearUsuarioResponse response =
                mapper.aResponse(result);

        assertEquals(
                100L,
                response.usuarioId()
        );
    }

    @Test
    void noDebeConvertirRequestNulo() {
        NullPointerException excepcion =
                assertThrows(
                        NullPointerException.class,
                        () -> mapper.aCommand(null)
                );

        assertEquals(
                "CrearUsuarioRequest no puede ser nulo",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebeConvertirCrearUsuarioResultNulo() {
        NullPointerException excepcion =
                assertThrows(
                        NullPointerException.class,
                        () -> mapper.aResponse(null)
                );

        assertEquals(
                "CrearUsuarioResult no puede ser nulo",
                excepcion.getMessage()
        );
    }
}
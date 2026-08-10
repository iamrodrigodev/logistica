package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.write.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.CorreoElectronicoYaRegistradoException;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.PersonaNoExisteException;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class WriteExceptionHandler {

    @ExceptionHandler(PersonaNoExisteException.class)
    public ResponseEntity<WriteErrorResponse> personaNoExiste(
            PersonaNoExisteException ex,
            HttpServletRequest request
    ) {

        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(CorreoElectronicoYaRegistradoException.class)
    public ResponseEntity<WriteErrorResponse> correoDuplicado(
            CorreoElectronicoYaRegistradoException ex,
            HttpServletRequest request
    ) {

        return construirRespuesta(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<WriteErrorResponse> argumentoInvalido(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WriteErrorResponse> errorGeneral(
            Exception ex,
            HttpServletRequest request
    ) {

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request
        );
    }

    private ResponseEntity<WriteErrorResponse> construirRespuesta(

            HttpStatus status,
            String mensaje,
            HttpServletRequest request

    ) {

        WriteErrorResponse response =
                new WriteErrorResponse(
                        status.value(),
                        status.getReasonPhrase(),
                        mensaje,
                        request.getRequestURI(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }

}
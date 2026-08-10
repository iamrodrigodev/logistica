package pe.com.mcalderon.logistica.identidades.gestionusuarios.infrastructure.adapter.in.usuario.read.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.application.exception.UsuarioNoExisteException;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class ReadExceptionHandler {

    @ExceptionHandler(UsuarioNoExisteException.class)
    public ResponseEntity<ReadErrorResponse> manejarUsuarioNoExiste(
            UsuarioNoExisteException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ReadErrorResponse response =
                new ReadErrorResponse(
                        status.value(),
                        status.getReasonPhrase(),
                        exception.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
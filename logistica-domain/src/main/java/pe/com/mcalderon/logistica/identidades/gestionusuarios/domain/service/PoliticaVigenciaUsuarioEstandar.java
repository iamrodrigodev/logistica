package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;

import java.time.LocalDate;

public final class PoliticaVigenciaUsuarioEstandar
        implements PoliticaVigenciaUsuario {

    private static final int DIAS_INTERNO_PERSONAL = 365;
    private static final int DIAS_INTERNO_ADMINISTRACION = 365;
    private static final int DIAS_INTERNO_SERVICIO = 365;
    private static final int DIAS_INTERNO_OTRO = 30;

    private static final int DIAS_EXTERNO_PERSONAL = 90;
    private static final int DIAS_EXTERNO_ADMINISTRACION = 10;
    private static final int DIAS_EXTERNO_SERVICIO = 60;
    private static final int DIAS_EXTERNO_OTRO = 10;

    @Override
    public PeriodoVigencia calcular(
            LocalDate fechaInicio,
            OrigenUsuario origenUsuario,
            TipoCuenta tipoCuenta
    ) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio de vigencia no puede ser nula"
            );
        }

        if (origenUsuario == null) {
            throw new IllegalArgumentException(
                    "El origen del usuario no puede ser nulo"
            );
        }

        if (tipoCuenta == null) {
            throw new IllegalArgumentException(
                    "El tipo de cuenta no puede ser nulo"
            );
        }

        int duracionDias = determinarDuracionDias(
                origenUsuario,
                tipoCuenta
        );

        LocalDate fechaFin =
                fechaInicio.plusDays(duracionDias - 1L);

        return new PeriodoVigencia(
                fechaInicio,
                fechaFin
        );
    }

    private int determinarDuracionDias(
            OrigenUsuario origenUsuario,
            TipoCuenta tipoCuenta
    ) {
        if (origenUsuario == OrigenUsuario.INTERNO) {
            return determinarDuracionInterna(tipoCuenta);
        }

        return determinarDuracionExterna(tipoCuenta);
    }

    private int determinarDuracionInterna(
            TipoCuenta tipoCuenta
    ) {
        return switch (tipoCuenta) {
            case PERSONAL -> DIAS_INTERNO_PERSONAL;
            case ADMINISTRACION -> DIAS_INTERNO_ADMINISTRACION;
            case SERVICIO -> DIAS_INTERNO_SERVICIO;
            case OTRO -> DIAS_INTERNO_OTRO;
        };
    }

    private int determinarDuracionExterna(
            TipoCuenta tipoCuenta
    ) {
        return switch (tipoCuenta) {
            case PERSONAL -> DIAS_EXTERNO_PERSONAL;
            case ADMINISTRACION -> DIAS_EXTERNO_ADMINISTRACION;
            case SERVICIO -> DIAS_EXTERNO_SERVICIO;
            case OTRO -> DIAS_EXTERNO_OTRO;
        };
    }
}
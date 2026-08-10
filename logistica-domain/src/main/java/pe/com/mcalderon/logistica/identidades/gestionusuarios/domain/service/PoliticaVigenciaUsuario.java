package pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.service;

import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.OrigenUsuario;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.PeriodoVigencia;
import pe.com.mcalderon.logistica.identidades.gestionusuarios.domain.model.TipoCuenta;

import java.time.LocalDate;

public interface PoliticaVigenciaUsuario {

    PeriodoVigencia calcular(
            LocalDate fechaInicio,
            OrigenUsuario origenUsuario,
            TipoCuenta tipoCuenta
    );
}
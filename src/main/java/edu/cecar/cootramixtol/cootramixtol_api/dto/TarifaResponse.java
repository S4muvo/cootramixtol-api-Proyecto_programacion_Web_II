package edu.cecar.cootramixtol.cootramixtol_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TarifaResponse(
        Long id,
        BigDecimal aportesGastosVarios,
        BigDecimal fondoReposicion,
        BigDecimal aporteSocialPorSocio,
        BigDecimal total,
        Boolean activo,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion
) {
}

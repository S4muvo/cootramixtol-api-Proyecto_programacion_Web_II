package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TarifaRequest(
        @NotNull @DecimalMin("0.00") BigDecimal aportesGastosVarios,
        @NotNull @DecimalMin("0.00") BigDecimal fondoReposicion,
        @NotNull @DecimalMin("0.00") BigDecimal aporteSocialPorSocio,
        @NotNull Boolean activo
) {
}

package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoPlanilla;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record PlanillaRequest(
        @NotNull LocalDate fecha,
        LocalTime hora,
        @NotBlank String vehiculoPlaca,
        Long tarifaId,
        @NotNull TipoPlanilla tipoPlanilla,
        @NotBlank String registradoPor
) {
}

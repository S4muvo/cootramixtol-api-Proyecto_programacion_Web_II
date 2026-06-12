package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoPlanilla;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record PlanillaMasivaRequest(
        @NotNull LocalDate fecha,
        @NotEmpty List<@NotBlank String> placas,
        @NotNull TipoPlanilla tipoPlanilla,
        @NotBlank String registradoPor
) {
}

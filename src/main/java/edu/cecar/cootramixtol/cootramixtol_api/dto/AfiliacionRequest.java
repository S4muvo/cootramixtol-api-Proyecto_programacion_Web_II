package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AfiliacionRequest(
        @NotBlank String asociadoIdentificacion,
        @NotBlank String vehiculoPlaca,
        @NotNull LocalDate fechaAfiliacion,
        LocalDate fechaFinAfiliacion,
        @NotNull EstadoAfiliacion estado
) {
}

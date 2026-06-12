package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record DespachoRequest(
        @NotBlank String ruta,
        @NotBlank String vehiculoPlaca,
        @NotNull LocalTime hora,
        @NotNull LocalDate fecha,
        @NotNull @Min(0) Integer numeroPasajeros
) {
}

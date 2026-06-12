package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record VehiculoRequest(
        @NotBlank String placa,
        @NotBlank String conductorIdentificacion,
        @NotNull TipoVehiculo tipo,
        @NotBlank String marca,
        @NotNull @Min(1900) Integer modelo,
        @NotNull @Min(1) Integer capacidadPasajeros,
        @NotBlank String color,
        @NotBlank String tarjetaPropiedad,
        @NotNull Long tarifaId,
        @NotNull Boolean activo,
        @NotNull LocalDate vigenciaSoat,
        @NotNull LocalDate vigenciaRtm,
        @NotNull LocalDate fechaIngreso
) {
}

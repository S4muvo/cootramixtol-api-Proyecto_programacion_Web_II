package edu.cecar.cootramixtol.cootramixtol_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DespachoResponse(
        Long id,
        String ruta,
        String vehiculoPlaca,
        LocalTime hora,
        LocalDate fecha,
        Integer numeroPasajeros,
        LocalDateTime fechaRegistro
) {
}

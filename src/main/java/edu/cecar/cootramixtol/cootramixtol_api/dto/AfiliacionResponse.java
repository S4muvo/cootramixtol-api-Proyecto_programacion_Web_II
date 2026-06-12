package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AfiliacionResponse(
        Long id,
        String asociadoIdentificacion,
        String asociadoNombres,
        String vehiculoPlaca,
        LocalDate fechaAfiliacion,
        LocalDate fechaFinAfiliacion,
        EstadoAfiliacion estado,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion
) {
}

package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoPlanilla;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record PlanillaResponse(
        Long numero,
        LocalDate fecha,
        LocalTime hora,
        String conductorIdentificacion,
        String conductorNombres,
        String vehiculoPlaca,
        Long tarifaId,
        BigDecimal tarifaTotal,
        TipoPlanilla tipoPlanilla,
        EstadoPlanilla estado,
        String registradoPor,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion
) {
}

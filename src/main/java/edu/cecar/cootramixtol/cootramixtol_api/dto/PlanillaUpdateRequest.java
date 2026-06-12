package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;

public record PlanillaUpdateRequest(
        String conductorIdentificacion,
        Long tarifaId,
        EstadoPlanilla estado
) {
}

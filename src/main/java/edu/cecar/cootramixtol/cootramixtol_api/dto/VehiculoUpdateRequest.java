package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import java.time.LocalDate;

public record VehiculoUpdateRequest(
        String conductorIdentificacion,
        TipoVehiculo tipo,
        String marca,
        Integer modelo,
        Integer capacidadPasajeros,
        String color,
        String tarjetaPropiedad,
        Long tarifaId,
        Boolean activo,
        LocalDate vigenciaSoat,
        LocalDate vigenciaRtm,
        LocalDate fechaIngreso
) {
}

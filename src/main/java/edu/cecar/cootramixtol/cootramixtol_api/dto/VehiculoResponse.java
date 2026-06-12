package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record VehiculoResponse(
        Long id,
        String placa,
        String conductorIdentificacion,
        String conductorNombres,
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
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion,
        LocalDate fechaIngreso
) {
}

package edu.cecar.cootramixtol.cootramixtol_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AsociadoResponse(
        Long id,
        String identificacion,
        String nombres,
        Boolean activo,
        String celular,
        String correo,
        LocalDate fechaNacimiento,
        LocalDate fechaIngreso,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion
) {
}

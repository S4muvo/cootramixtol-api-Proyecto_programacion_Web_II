package edu.cecar.cootramixtol.cootramixtol_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConductorResponse(
        Long id,
        String identificacion,
        String nombres,
        LocalDate fechaNacimiento,
        Boolean activo,
        String numeroLicencia,
        String categoriaLicencia,
        LocalDate vigenciaLicencia,
        String celular,
        String correo,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaFechaActualizacion
) {
}

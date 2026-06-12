package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.Email;
import java.time.LocalDate;

public record ConductorUpdateRequest(
        String nombres,
        LocalDate fechaNacimiento,
        Boolean activo,
        String numeroLicencia,
        String categoriaLicencia,
        LocalDate vigenciaLicencia,
        String celular,
        @Email String correo
) {
}

package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ConductorRequest(
        @NotBlank String identificacion,
        @NotBlank String nombres,
        @NotNull LocalDate fechaNacimiento,
        @NotNull Boolean activo,
        @NotBlank String numeroLicencia,
        @NotBlank String categoriaLicencia,
        @NotNull LocalDate vigenciaLicencia,
        @NotBlank String celular,
        @NotBlank @Email String correo
) {
}

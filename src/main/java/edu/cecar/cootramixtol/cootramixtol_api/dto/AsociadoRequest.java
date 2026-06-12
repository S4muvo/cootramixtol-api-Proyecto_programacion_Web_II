package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AsociadoRequest(
        @NotBlank String identificacion,
        @NotBlank String nombres,
        @NotNull Boolean activo,
        @NotBlank String celular,
        @NotBlank @Email String correo,
        @NotNull LocalDate fechaNacimiento,
        @NotNull LocalDate fechaIngreso
) {
}

package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.Email;
import java.time.LocalDate;

public record AsociadoUpdateRequest(
        String nombres,
        Boolean activo,
        String celular,
        @Email String correo,
        LocalDate fechaNacimiento,
        LocalDate fechaIngreso
) {
}

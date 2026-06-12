package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String identificacion,
        @NotNull Rol rol,
        String asociadoIdentificacion
) {
}

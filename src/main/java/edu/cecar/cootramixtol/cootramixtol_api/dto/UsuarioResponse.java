package edu.cecar.cootramixtol.cootramixtol_api.dto;

import edu.cecar.cootramixtol.cootramixtol_api.enums.Rol;
import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String username,
        String identificacion,
        Rol rol,
        Boolean activo,
        String asociadoIdentificacion,
        LocalDateTime fechaRegistro
) {
}

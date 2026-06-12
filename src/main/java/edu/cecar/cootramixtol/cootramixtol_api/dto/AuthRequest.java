package edu.cecar.cootramixtol.cootramixtol_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank String username, @NotBlank String password) {
}

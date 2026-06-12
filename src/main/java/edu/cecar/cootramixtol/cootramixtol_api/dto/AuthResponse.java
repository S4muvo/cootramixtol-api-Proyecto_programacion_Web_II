package edu.cecar.cootramixtol.cootramixtol_api.dto;

import java.time.Instant;

public record AuthResponse(String tokenType, String accessToken, Instant expiresAt, String identificacion, String rol) {
}

package edu.cecar.cootramixtol.cootramixtol_api.security;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import java.time.Instant;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String generateToken(Usuario usuario);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);

    Instant expiresAt();
}

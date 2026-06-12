package edu.cecar.cootramixtol.cootramixtol_api.security;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtTokenServiceImpl(@Value("${app.jwt.secret:}") String secret, @Value("${app.jwt.expiration-minutes:60}") long expirationMinutes) {
        this.secretKey = buildKey(secret);
        this.expirationMinutes = expirationMinutes;
    }

    @Override
    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(expirationMinutes, ChronoUnit.MINUTES);
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("identificacion", usuario.getIdentificacion())
                .claim("rol", usuario.getRol().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && claims(token).getExpiration().after(new Date());
    }

    @Override
    public String extractUsername(String token) {
        return claims(token).getSubject();
    }

    @Override
    public Instant expiresAt() {
        return Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES);
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey buildKey(String secret) {
        if (secret == null || secret.isBlank()) {
            return Jwts.SIG.HS256.key().build();
        }
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("JWT_SECRET debe tener al menos 32 caracteres.");
        }
        return Keys.hmacShaKeyFor(bytes);
    }
}

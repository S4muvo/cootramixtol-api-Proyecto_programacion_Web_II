package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AuthRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AuthResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.UsuarioRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.UsuarioResponse;
import edu.cecar.cootramixtol.cootramixtol_api.security.JwtTokenService;
import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import edu.cecar.cootramixtol.cootramixtol_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtTokenService jwtTokenService;
    private final StringSanitizer sanitizer;

    public AuthController(AuthenticationManager authenticationManager, UsuarioService usuarioService, JwtTokenService jwtTokenService, StringSanitizer sanitizer) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtTokenService = jwtTokenService;
        this.sanitizer = sanitizer;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sanitizer.sanitize(request.username()), request.password()));
        Usuario usuario = usuarioService.buscarPorUsername(sanitizer.sanitize(request.username()));
        String token = jwtTokenService.generateToken(usuario);
        return ResponseEntity.ok(new AuthResponse("Bearer", token, jwtTokenService.expiresAt(), usuario.getIdentificacion(), usuario.getRol().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(sanitizer.sanitize(request.username()));
        usuario.setPassword(request.password());
        usuario.setIdentificacion(sanitizer.sanitize(request.identificacion()));
        usuario.setRol(request.rol());
        Usuario creado = usuarioService.registrar(usuario, sanitizer.sanitize(request.asociadoIdentificacion()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(creado));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        String asociadoIdentificacion = usuario.getAsociado() != null ? usuario.getAsociado().getIdentificacion() : null;
        return new UsuarioResponse(usuario.getId(), usuario.getUsername(), usuario.getIdentificacion(), usuario.getRol(), usuario.getActivo(), asociadoIdentificacion, usuario.getFechaRegistro());
    }
}

package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import edu.cecar.cootramixtol.cootramixtol_api.enums.Rol;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.AsociadoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.UsuarioRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.UsuarioService;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AsociadoRepository asociadoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, AsociadoRepository asociadoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.asociadoRepository = asociadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario registrar(Usuario usuario, String asociadoIdentificacion) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new BusinessException("Ya existe un usuario con ese nombre de usuario.");
        }
        if (usuarioRepository.existsByIdentificacion(usuario.getIdentificacion())) {
            throw new BusinessException("Ya existe un usuario con esa identificación.");
        }
        if (Rol.ASOCIADO.equals(usuario.getRol())) {
            Asociado asociado = asociadoRepository.findByIdentificacion(asociadoIdentificacion)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe el asociado indicado."));
            usuario.setAsociado(asociado);
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario indicado."));
    }
}

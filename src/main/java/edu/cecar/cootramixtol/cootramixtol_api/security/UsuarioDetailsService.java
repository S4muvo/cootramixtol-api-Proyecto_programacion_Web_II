package edu.cecar.cootramixtol.cootramixtol_api.security;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import edu.cecar.cootramixtol.cootramixtol_api.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getActivo(), true, true, true, List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())));
    }
}

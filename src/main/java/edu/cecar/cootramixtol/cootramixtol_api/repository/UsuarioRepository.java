package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdentificacion(String identificacion);
}

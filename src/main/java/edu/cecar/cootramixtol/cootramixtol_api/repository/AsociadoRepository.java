package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsociadoRepository extends JpaRepository<Asociado, Long> {

    Optional<Asociado> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCelular(String celular);

    boolean existsByCorreo(String correo);

    List<Asociado> findByNombresContainingIgnoreCaseAndActivo(String nombres, Boolean activo);

    List<Asociado> findByNombresContainingIgnoreCase(String nombres);

    List<Asociado> findByActivo(Boolean activo);
}

package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    Optional<Conductor> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByNumeroLicencia(String numeroLicencia);

    boolean existsByCelular(String celular);

    boolean existsByCorreo(String correo);

    List<Conductor> findByNombresContainingIgnoreCaseAndActivo(String nombres, Boolean activo);

    List<Conductor> findByNombresContainingIgnoreCase(String nombres);

    List<Conductor> findByActivo(Boolean activo);
}

package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Afiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfiliacionRepository extends JpaRepository<Afiliacion, Long> {

    boolean existsByVehiculoPlacaAndEstado(String placa, EstadoAfiliacion estado);

    List<Afiliacion> findByAsociadoIdentificacion(String asociadoIdentificacion);

    List<Afiliacion> findByVehiculoPlaca(String vehiculoPlaca);

    List<Afiliacion> findByEstado(EstadoAfiliacion estado);

    List<Afiliacion> findByFechaAfiliacion(LocalDate fechaAfiliacion);

    List<Afiliacion> findByFechaFinAfiliacion(LocalDate fechaFinAfiliacion);
}

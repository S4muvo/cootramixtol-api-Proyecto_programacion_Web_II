package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Planilla;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanillaRepository extends JpaRepository<Planilla, Long> {

    List<Planilla> findByFecha(LocalDate fecha);

    List<Planilla> findByVehiculoPlaca(String placa);

    List<Planilla> findByEstado(EstadoPlanilla estado);
}

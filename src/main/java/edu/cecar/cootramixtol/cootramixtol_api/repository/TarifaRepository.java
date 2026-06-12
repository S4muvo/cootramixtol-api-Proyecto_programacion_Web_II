package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    List<Tarifa> findByActivo(Boolean activo);

    Optional<Tarifa> findFirstByTotalAndActivo(BigDecimal total, Boolean activo);
}

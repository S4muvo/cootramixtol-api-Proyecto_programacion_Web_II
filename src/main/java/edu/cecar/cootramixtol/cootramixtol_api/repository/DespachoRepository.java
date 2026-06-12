package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Despacho;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    List<Despacho> findByFecha(LocalDate fecha);

    List<Despacho> findByVehiculoPlaca(String placa);

    List<Despacho> findByRutaContainingIgnoreCase(String ruta);
}

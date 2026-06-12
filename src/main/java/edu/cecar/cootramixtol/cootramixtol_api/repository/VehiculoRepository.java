package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    boolean existsByTarjetaPropiedad(String tarjetaPropiedad);

    boolean existsByConductorIdentificacion(String identificacion);

    List<Vehiculo> findByPlacaContainingIgnoreCaseAndTipoAndActivo(String placa, TipoVehiculo tipo, Boolean activo);

    List<Vehiculo> findByPlacaContainingIgnoreCaseAndTipo(String placa, TipoVehiculo tipo);

    List<Vehiculo> findByPlacaContainingIgnoreCaseAndActivo(String placa, Boolean activo);

    List<Vehiculo> findByTipoAndActivo(TipoVehiculo tipo, Boolean activo);

    List<Vehiculo> findByPlacaContainingIgnoreCase(String placa);

    List<Vehiculo> findByTipo(TipoVehiculo tipo);

    List<Vehiculo> findByActivo(Boolean activo);
}

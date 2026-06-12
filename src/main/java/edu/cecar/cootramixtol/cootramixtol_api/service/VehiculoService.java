package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import java.util.List;

public interface VehiculoService {

    Vehiculo registrar(Vehiculo vehiculo, String conductorIdentificacion, Long tarifaId);

    Vehiculo buscarPorPlaca(String placa);

    List<Vehiculo> filtrar(String placa, TipoVehiculo tipo, Boolean activo);

    Vehiculo actualizar(String placa, Vehiculo datos, String conductorIdentificacion, Long tarifaId);
}

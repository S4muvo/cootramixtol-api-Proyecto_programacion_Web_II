package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import java.util.List;

public interface TarifaService {

    Tarifa registrar(Tarifa tarifa);

    Tarifa buscarPorId(Long id);

    List<Tarifa> filtrar(Boolean activo);

    Tarifa inactivar(Long id);

    Tarifa buscarTarifaCero();
}

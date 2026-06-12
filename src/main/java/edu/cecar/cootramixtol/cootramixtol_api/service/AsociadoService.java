package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import java.util.List;

public interface AsociadoService {

    Asociado registrar(Asociado asociado);

    Asociado buscarPorIdentificacion(String identificacion);

    List<Asociado> filtrar(String nombres, Boolean activo);

    Asociado actualizar(String identificacion, Asociado datos);
}

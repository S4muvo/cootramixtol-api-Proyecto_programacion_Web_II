package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import java.util.List;

public interface ConductorService {

    Conductor registrar(Conductor conductor);

    Conductor buscarPorIdentificacion(String identificacion);

    List<Conductor> filtrar(String nombres, Boolean activo);

    Conductor actualizar(String identificacion, Conductor datos);
}

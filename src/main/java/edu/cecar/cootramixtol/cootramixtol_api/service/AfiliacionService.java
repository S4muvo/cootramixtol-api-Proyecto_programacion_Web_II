package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Afiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import java.time.LocalDate;
import java.util.List;

public interface AfiliacionService {

    Afiliacion registrar(Afiliacion afiliacion, String asociadoIdentificacion, String vehiculoPlaca);

    Afiliacion buscarPorId(Long id);

    List<Afiliacion> filtrar(String asociadoIdentificacion, String vehiculoPlaca, EstadoAfiliacion estado, LocalDate fechaAfiliacion, LocalDate fechaFinAfiliacion);

    Afiliacion desafiliar(Long id, LocalDate fechaFinAfiliacion);
}

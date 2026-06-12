package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Planilla;
import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoPlanilla;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PlanillaService {

    Planilla registrar(LocalDate fecha, LocalTime hora, String vehiculoPlaca, Long tarifaId, TipoPlanilla tipoPlanilla, String registradoPor);

    List<Planilla> registrarMasivo(LocalDate fecha, List<String> placas, TipoPlanilla tipoPlanilla, String registradoPor);

    Planilla buscarPorNumero(Long numero);

    List<Planilla> filtrar(LocalDate fecha, String vehiculoPlaca, EstadoPlanilla estado);

    Planilla actualizar(Long numero, String conductorIdentificacion, Long tarifaId, EstadoPlanilla estado);
}

package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Despacho;
import java.time.LocalDate;
import java.util.List;

public interface DespachoService {

    Despacho registrar(Despacho despacho, String vehiculoPlaca);

    List<Despacho> filtrar(LocalDate fecha, String vehiculoPlaca, String ruta);
}

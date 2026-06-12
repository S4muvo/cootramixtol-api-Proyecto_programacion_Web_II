package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import edu.cecar.cootramixtol.cootramixtol_api.dto.TarifaRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.TarifaResponse;
import edu.cecar.cootramixtol.cootramixtol_api.service.TarifaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<TarifaResponse> registrar(@Valid @RequestBody TarifaRequest request) {
        Tarifa tarifa = new Tarifa();
        tarifa.setAportesGastosVarios(request.aportesGastosVarios());
        tarifa.setFondoReposicion(request.fondoReposicion());
        tarifa.setAporteSocialPorSocio(request.aporteSocialPorSocio());
        tarifa.setActivo(request.activo());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(tarifaService.registrar(tarifa)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<TarifaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(tarifaService.buscarPorId(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<TarifaResponse>> filtrar(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(tarifaService.filtrar(activo).stream().map(this::toResponse).toList());
    }

    @PatchMapping("/{id}/inactivar")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<TarifaResponse> inactivar(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(tarifaService.inactivar(id)));
    }

    private TarifaResponse toResponse(Tarifa tarifa) {
        return new TarifaResponse(tarifa.getId(), tarifa.getAportesGastosVarios(), tarifa.getFondoReposicion(), tarifa.getAporteSocialPorSocio(), tarifa.getTotal(), tarifa.getActivo(), tarifa.getFechaRegistro(), tarifa.getUltimaFechaActualizacion());
    }
}

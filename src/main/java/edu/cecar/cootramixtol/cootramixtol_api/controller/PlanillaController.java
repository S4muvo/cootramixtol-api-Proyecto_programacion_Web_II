package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Planilla;
import edu.cecar.cootramixtol.cootramixtol_api.dto.PlanillaMasivaRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.PlanillaRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.PlanillaResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.PlanillaUpdateRequest;
import edu.cecar.cootramixtol.cootramixtol_api.service.PlanillaService;
import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
@RequestMapping("/api/planillas")
public class PlanillaController {

    private final PlanillaService planillaService;
    private final StringSanitizer sanitizer;

    public PlanillaController(PlanillaService planillaService, StringSanitizer sanitizer) {
        this.planillaService = planillaService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<PlanillaResponse> registrar(@Valid @RequestBody PlanillaRequest request) {
        Planilla planilla = planillaService.registrar(request.fecha(), request.hora(), sanitizer.sanitize(request.vehiculoPlaca()).toUpperCase(), request.tarifaId(), request.tipoPlanilla(), sanitizer.sanitize(request.registradoPor()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(planilla));
    }

    @PostMapping("/masivas")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<PlanillaResponse>> registrarMasivo(@Valid @RequestBody PlanillaMasivaRequest request) {
        List<String> placas = request.placas().stream().map(placa -> sanitizer.sanitize(placa).toUpperCase()).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(planillaService.registrarMasivo(request.fecha(), placas, request.tipoPlanilla(), sanitizer.sanitize(request.registradoPor())).stream().map(this::toResponse).toList());
    }

    @GetMapping("/{numero}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<PlanillaResponse> buscar(@PathVariable Long numero) {
        return ResponseEntity.ok(toResponse(planillaService.buscarPorNumero(numero)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<PlanillaResponse>> filtrar(@RequestParam(required = false) LocalDate fecha, @RequestParam(required = false) String vehiculoPlaca, @RequestParam(required = false) EstadoPlanilla estado) {
        String placa = vehiculoPlaca != null ? sanitizer.sanitize(vehiculoPlaca).toUpperCase() : null;
        return ResponseEntity.ok(planillaService.filtrar(fecha, placa, estado).stream().map(this::toResponse).toList());
    }

    @PatchMapping("/{numero}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<PlanillaResponse> actualizar(@PathVariable Long numero, @Valid @RequestBody PlanillaUpdateRequest request) {
        return ResponseEntity.ok(toResponse(planillaService.actualizar(numero, sanitizer.sanitize(request.conductorIdentificacion()), request.tarifaId(), request.estado())));
    }

    private PlanillaResponse toResponse(Planilla planilla) {
        return new PlanillaResponse(planilla.getNumero(), planilla.getFecha(), planilla.getHora(), planilla.getConductor().getIdentificacion(), planilla.getConductor().getNombres(), planilla.getVehiculo().getPlaca(), planilla.getTarifa().getId(), planilla.getTarifa().getTotal(), planilla.getTipoPlanilla(), planilla.getEstado(), planilla.getRegistradoPor(), planilla.getFechaRegistro(), planilla.getUltimaFechaActualizacion());
    }
}

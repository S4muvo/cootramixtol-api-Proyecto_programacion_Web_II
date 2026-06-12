package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Afiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AfiliacionRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AfiliacionResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.DesafiliacionRequest;
import edu.cecar.cootramixtol.cootramixtol_api.service.AfiliacionService;
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
@RequestMapping("/api/afiliaciones")
public class AfiliacionController {

    private final AfiliacionService afiliacionService;
    private final StringSanitizer sanitizer;

    public AfiliacionController(AfiliacionService afiliacionService, StringSanitizer sanitizer) {
        this.afiliacionService = afiliacionService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AfiliacionResponse> registrar(@Valid @RequestBody AfiliacionRequest request) {
        Afiliacion afiliacion = new Afiliacion();
        afiliacion.setFechaAfiliacion(request.fechaAfiliacion());
        afiliacion.setFechaFinAfiliacion(request.fechaFinAfiliacion());
        afiliacion.setEstado(request.estado());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(afiliacionService.registrar(afiliacion, sanitizer.sanitize(request.asociadoIdentificacion()), sanitizer.sanitize(request.vehiculoPlaca()).toUpperCase())));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AfiliacionResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(afiliacionService.buscarPorId(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<AfiliacionResponse>> filtrar(@RequestParam(required = false) String asociadoIdentificacion, @RequestParam(required = false) String vehiculoPlaca, @RequestParam(required = false) EstadoAfiliacion estado, @RequestParam(required = false) LocalDate fechaAfiliacion, @RequestParam(required = false) LocalDate fechaFinAfiliacion) {
        String placa = vehiculoPlaca != null ? sanitizer.sanitize(vehiculoPlaca).toUpperCase() : null;
        return ResponseEntity.ok(afiliacionService.filtrar(sanitizer.sanitize(asociadoIdentificacion), placa, estado, fechaAfiliacion, fechaFinAfiliacion).stream().map(this::toResponse).toList());
    }

    @PatchMapping("/{id}/desafiliar")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AfiliacionResponse> desafiliar(@PathVariable Long id, @RequestBody(required = false) DesafiliacionRequest request) {
        LocalDate fechaFin = request != null ? request.fechaFinAfiliacion() : null;
        return ResponseEntity.ok(toResponse(afiliacionService.desafiliar(id, fechaFin)));
    }

    private AfiliacionResponse toResponse(Afiliacion afiliacion) {
        return new AfiliacionResponse(afiliacion.getId(), afiliacion.getAsociado().getIdentificacion(), afiliacion.getAsociado().getNombres(), afiliacion.getVehiculo().getPlaca(), afiliacion.getFechaAfiliacion(), afiliacion.getFechaFinAfiliacion(), afiliacion.getEstado(), afiliacion.getFechaRegistro(), afiliacion.getUltimaFechaActualizacion());
    }
}

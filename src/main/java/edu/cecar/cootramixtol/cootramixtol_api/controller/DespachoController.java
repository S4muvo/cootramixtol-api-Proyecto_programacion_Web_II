package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Despacho;
import edu.cecar.cootramixtol.cootramixtol_api.dto.DespachoRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.DespachoResponse;
import edu.cecar.cootramixtol.cootramixtol_api.service.DespachoService;
import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {

    private final DespachoService despachoService;
    private final StringSanitizer sanitizer;

    public DespachoController(DespachoService despachoService, StringSanitizer sanitizer) {
        this.despachoService = despachoService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<DespachoResponse> registrar(@Valid @RequestBody DespachoRequest request) {
        Despacho despacho = new Despacho();
        despacho.setRuta(sanitizer.sanitize(request.ruta()));
        despacho.setHora(request.hora());
        despacho.setFecha(request.fecha());
        despacho.setNumeroPasajeros(request.numeroPasajeros());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(despachoService.registrar(despacho, sanitizer.sanitize(request.vehiculoPlaca()).toUpperCase())));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE','ASOCIADO')")
    public ResponseEntity<List<DespachoResponse>> filtrar(@RequestParam(required = false) LocalDate fecha, @RequestParam(required = false) String vehiculoPlaca, @RequestParam(required = false) String ruta) {
        String placa = vehiculoPlaca != null ? sanitizer.sanitize(vehiculoPlaca).toUpperCase() : null;
        return ResponseEntity.ok(despachoService.filtrar(fecha, placa, sanitizer.sanitize(ruta)).stream().map(this::toResponse).toList());
    }

    private DespachoResponse toResponse(Despacho despacho) {
        return new DespachoResponse(despacho.getId(), despacho.getRuta(), despacho.getVehiculo().getPlaca(), despacho.getHora(), despacho.getFecha(), despacho.getNumeroPasajeros(), despacho.getFechaRegistro());
    }
}

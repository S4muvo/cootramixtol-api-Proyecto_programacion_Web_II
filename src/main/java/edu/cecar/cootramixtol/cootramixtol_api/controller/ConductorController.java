package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import edu.cecar.cootramixtol.cootramixtol_api.dto.ConductorRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.ConductorResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.ConductorUpdateRequest;
import edu.cecar.cootramixtol.cootramixtol_api.service.ConductorService;
import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService conductorService;
    private final StringSanitizer sanitizer;

    public ConductorController(ConductorService conductorService, StringSanitizer sanitizer) {
        this.conductorService = conductorService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<ConductorResponse> registrar(@Valid @RequestBody ConductorRequest request) {
        Conductor conductor = new Conductor();
        conductor.setIdentificacion(sanitizer.sanitize(request.identificacion()));
        conductor.setNombres(sanitizer.sanitize(request.nombres()));
        conductor.setFechaNacimiento(request.fechaNacimiento());
        conductor.setActivo(request.activo());
        conductor.setNumeroLicencia(sanitizer.sanitize(request.numeroLicencia()));
        conductor.setCategoriaLicencia(sanitizer.sanitize(request.categoriaLicencia()));
        conductor.setVigenciaLicencia(request.vigenciaLicencia());
        conductor.setCelular(sanitizer.sanitize(request.celular()));
        conductor.setCorreo(sanitizer.sanitize(request.correo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(conductorService.registrar(conductor)));
    }

    @GetMapping("/{identificacion}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<ConductorResponse> buscar(@PathVariable String identificacion) {
        return ResponseEntity.ok(toResponse(conductorService.buscarPorIdentificacion(sanitizer.sanitize(identificacion))));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<ConductorResponse>> filtrar(@RequestParam(required = false) String nombres, @RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(conductorService.filtrar(sanitizer.sanitize(nombres), activo).stream().map(this::toResponse).toList());
    }

    @PutMapping("/{identificacion}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<ConductorResponse> actualizar(@PathVariable String identificacion, @Valid @RequestBody ConductorUpdateRequest request) {
        Conductor datos = new Conductor();
        datos.setNombres(sanitizer.sanitize(request.nombres()));
        datos.setFechaNacimiento(request.fechaNacimiento());
        datos.setActivo(request.activo());
        datos.setNumeroLicencia(sanitizer.sanitize(request.numeroLicencia()));
        datos.setCategoriaLicencia(sanitizer.sanitize(request.categoriaLicencia()));
        datos.setVigenciaLicencia(request.vigenciaLicencia());
        datos.setCelular(sanitizer.sanitize(request.celular()));
        datos.setCorreo(sanitizer.sanitize(request.correo()));
        return ResponseEntity.ok(toResponse(conductorService.actualizar(sanitizer.sanitize(identificacion), datos)));
    }

    private ConductorResponse toResponse(Conductor conductor) {
        return new ConductorResponse(conductor.getId(), conductor.getIdentificacion(), conductor.getNombres(), conductor.getFechaNacimiento(), conductor.getActivo(), conductor.getNumeroLicencia(), conductor.getCategoriaLicencia(), conductor.getVigenciaLicencia(), conductor.getCelular(), conductor.getCorreo(), conductor.getFechaRegistro(), conductor.getUltimaFechaActualizacion());
    }
}

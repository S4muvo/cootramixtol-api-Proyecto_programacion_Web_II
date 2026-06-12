package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AsociadoRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AsociadoResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.AsociadoUpdateRequest;
import edu.cecar.cootramixtol.cootramixtol_api.service.AsociadoService;
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
@RequestMapping("/api/asociados")
public class AsociadoController {

    private final AsociadoService asociadoService;
    private final StringSanitizer sanitizer;

    public AsociadoController(AsociadoService asociadoService, StringSanitizer sanitizer) {
        this.asociadoService = asociadoService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AsociadoResponse> registrar(@Valid @RequestBody AsociadoRequest request) {
        Asociado asociado = new Asociado();
        asociado.setIdentificacion(sanitizer.sanitize(request.identificacion()));
        asociado.setNombres(sanitizer.sanitize(request.nombres()));
        asociado.setActivo(request.activo());
        asociado.setCelular(sanitizer.sanitize(request.celular()));
        asociado.setCorreo(sanitizer.sanitize(request.correo()));
        asociado.setFechaNacimiento(request.fechaNacimiento());
        asociado.setFechaIngreso(request.fechaIngreso());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(asociadoService.registrar(asociado)));
    }

    @GetMapping("/{identificacion}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AsociadoResponse> buscar(@PathVariable String identificacion) {
        return ResponseEntity.ok(toResponse(asociadoService.buscarPorIdentificacion(sanitizer.sanitize(identificacion))));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<AsociadoResponse>> filtrar(@RequestParam(required = false) String nombres, @RequestParam(required = false) Boolean activo) {
        List<AsociadoResponse> response = asociadoService.filtrar(sanitizer.sanitize(nombres), activo).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{identificacion}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<AsociadoResponse> actualizar(@PathVariable String identificacion, @Valid @RequestBody AsociadoUpdateRequest request) {
        Asociado datos = new Asociado();
        datos.setNombres(sanitizer.sanitize(request.nombres()));
        datos.setActivo(request.activo());
        datos.setCelular(sanitizer.sanitize(request.celular()));
        datos.setCorreo(sanitizer.sanitize(request.correo()));
        datos.setFechaNacimiento(request.fechaNacimiento());
        datos.setFechaIngreso(request.fechaIngreso());
        return ResponseEntity.ok(toResponse(asociadoService.actualizar(sanitizer.sanitize(identificacion), datos)));
    }

    private AsociadoResponse toResponse(Asociado asociado) {
        return new AsociadoResponse(asociado.getId(), asociado.getIdentificacion(), asociado.getNombres(), asociado.getActivo(), asociado.getCelular(), asociado.getCorreo(), asociado.getFechaNacimiento(), asociado.getFechaIngreso(), asociado.getFechaRegistro(), asociado.getUltimaFechaActualizacion());
    }
}

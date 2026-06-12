package edu.cecar.cootramixtol.cootramixtol_api.controller;

import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.dto.VehiculoRequest;
import edu.cecar.cootramixtol.cootramixtol_api.dto.VehiculoResponse;
import edu.cecar.cootramixtol.cootramixtol_api.dto.VehiculoUpdateRequest;
import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import edu.cecar.cootramixtol.cootramixtol_api.service.VehiculoService;
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
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final StringSanitizer sanitizer;

    public VehiculoController(VehiculoService vehiculoService, StringSanitizer sanitizer) {
        this.vehiculoService = vehiculoService;
        this.sanitizer = sanitizer;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<VehiculoResponse> registrar(@Valid @RequestBody VehiculoRequest request) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(sanitizer.sanitize(request.placa()).toUpperCase());
        vehiculo.setTipo(request.tipo());
        vehiculo.setMarca(sanitizer.sanitize(request.marca()));
        vehiculo.setModelo(request.modelo());
        vehiculo.setCapacidadPasajeros(request.capacidadPasajeros());
        vehiculo.setColor(sanitizer.sanitize(request.color()));
        vehiculo.setTarjetaPropiedad(sanitizer.sanitize(request.tarjetaPropiedad()));
        vehiculo.setActivo(request.activo());
        vehiculo.setVigenciaSoat(request.vigenciaSoat());
        vehiculo.setVigenciaRtm(request.vigenciaRtm());
        vehiculo.setFechaIngreso(request.fechaIngreso());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(vehiculoService.registrar(vehiculo, sanitizer.sanitize(request.conductorIdentificacion()), request.tarifaId())));
    }

    @GetMapping("/{placa}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE','ASOCIADO')")
    public ResponseEntity<VehiculoResponse> buscar(@PathVariable String placa) {
        return ResponseEntity.ok(toResponse(vehiculoService.buscarPorPlaca(sanitizer.sanitize(placa).toUpperCase())));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<List<VehiculoResponse>> filtrar(@RequestParam(required = false) String placa, @RequestParam(required = false) TipoVehiculo tipo, @RequestParam(required = false) Boolean activo) {
        String placaSanitizada = placa != null ? sanitizer.sanitize(placa).toUpperCase() : null;
        return ResponseEntity.ok(vehiculoService.filtrar(placaSanitizada, tipo, activo).stream().map(this::toResponse).toList());
    }

    @PutMapping("/{placa}")
    @PreAuthorize("hasAnyRole('GERENTE','ASISTENTE')")
    public ResponseEntity<VehiculoResponse> actualizar(@PathVariable String placa, @Valid @RequestBody VehiculoUpdateRequest request) {
        Vehiculo datos = new Vehiculo();
        datos.setTipo(request.tipo());
        datos.setMarca(sanitizer.sanitize(request.marca()));
        datos.setModelo(request.modelo());
        datos.setCapacidadPasajeros(request.capacidadPasajeros());
        datos.setColor(sanitizer.sanitize(request.color()));
        datos.setTarjetaPropiedad(sanitizer.sanitize(request.tarjetaPropiedad()));
        datos.setActivo(request.activo());
        datos.setVigenciaSoat(request.vigenciaSoat());
        datos.setVigenciaRtm(request.vigenciaRtm());
        datos.setFechaIngreso(request.fechaIngreso());
        return ResponseEntity.ok(toResponse(vehiculoService.actualizar(sanitizer.sanitize(placa).toUpperCase(), datos, sanitizer.sanitize(request.conductorIdentificacion()), request.tarifaId())));
    }

    private VehiculoResponse toResponse(Vehiculo vehiculo) {
        return new VehiculoResponse(vehiculo.getId(), vehiculo.getPlaca(), vehiculo.getConductor().getIdentificacion(), vehiculo.getConductor().getNombres(), vehiculo.getTipo(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getCapacidadPasajeros(), vehiculo.getColor(), vehiculo.getTarjetaPropiedad(), vehiculo.getTarifa().getId(), vehiculo.getActivo(), vehiculo.getVigenciaSoat(), vehiculo.getVigenciaRtm(), vehiculo.getFechaRegistro(), vehiculo.getUltimaFechaActualizacion(), vehiculo.getFechaIngreso());
    }
}

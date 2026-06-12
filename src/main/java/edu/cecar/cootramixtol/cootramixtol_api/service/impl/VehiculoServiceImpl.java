package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoVehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.ConductorRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.TarifaRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.VehiculoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.VehiculoService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ConductorRepository conductorRepository;
    private final TarifaRepository tarifaRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, ConductorRepository conductorRepository, TarifaRepository tarifaRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.conductorRepository = conductorRepository;
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    @Transactional
    public Vehiculo registrar(Vehiculo vehiculo, String conductorIdentificacion, Long tarifaId) {
        if (vehiculoRepository.existsByPlaca(vehiculo.getPlaca())) {
            throw new BusinessException("Ya existe un vehículo con esa placa.");
        }
        if (vehiculoRepository.existsByTarjetaPropiedad(vehiculo.getTarjetaPropiedad())) {
            throw new BusinessException("Ya existe un vehículo con esa tarjeta de propiedad.");
        }
        vehiculo.setConductor(obtenerConductorDisponible(conductorIdentificacion, null));
        vehiculo.setTarifa(obtenerTarifaActiva(tarifaId));
        LocalDateTime now = LocalDateTime.now();
        vehiculo.setFechaRegistro(now);
        vehiculo.setUltimaFechaActualizacion(now);
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un vehículo con la placa indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> filtrar(String placa, TipoVehiculo tipo, Boolean activo) {
        if (placa != null && tipo != null && activo != null) {
            return vehiculoRepository.findByPlacaContainingIgnoreCaseAndTipoAndActivo(placa, tipo, activo);
        }
        if (placa != null && tipo != null) {
            return vehiculoRepository.findByPlacaContainingIgnoreCaseAndTipo(placa, tipo);
        }
        if (placa != null && activo != null) {
            return vehiculoRepository.findByPlacaContainingIgnoreCaseAndActivo(placa, activo);
        }
        if (tipo != null && activo != null) {
            return vehiculoRepository.findByTipoAndActivo(tipo, activo);
        }
        if (placa != null) {
            return vehiculoRepository.findByPlacaContainingIgnoreCase(placa);
        }
        if (tipo != null) {
            return vehiculoRepository.findByTipo(tipo);
        }
        if (activo != null) {
            return vehiculoRepository.findByActivo(activo);
        }
        return vehiculoRepository.findAll();
    }

    @Override
    @Transactional
    public Vehiculo actualizar(String placa, Vehiculo datos, String conductorIdentificacion, Long tarifaId) {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        if (datos.getTarjetaPropiedad() != null && !datos.getTarjetaPropiedad().equals(vehiculo.getTarjetaPropiedad()) && vehiculoRepository.existsByTarjetaPropiedad(datos.getTarjetaPropiedad())) {
            throw new BusinessException("Ya existe un vehículo con esa tarjeta de propiedad.");
        }
        if (conductorIdentificacion != null) {
            vehiculo.setConductor(obtenerConductorDisponible(conductorIdentificacion, vehiculo.getId()));
        }
        if (tarifaId != null) {
            vehiculo.setTarifa(obtenerTarifaActiva(tarifaId));
        }
        if (datos.getTipo() != null) {
            vehiculo.setTipo(datos.getTipo());
        }
        if (datos.getMarca() != null) {
            vehiculo.setMarca(datos.getMarca());
        }
        if (datos.getModelo() != null) {
            vehiculo.setModelo(datos.getModelo());
        }
        if (datos.getCapacidadPasajeros() != null) {
            vehiculo.setCapacidadPasajeros(datos.getCapacidadPasajeros());
        }
        if (datos.getColor() != null) {
            vehiculo.setColor(datos.getColor());
        }
        if (datos.getTarjetaPropiedad() != null) {
            vehiculo.setTarjetaPropiedad(datos.getTarjetaPropiedad());
        }
        if (datos.getActivo() != null) {
            vehiculo.setActivo(datos.getActivo());
        }
        if (datos.getVigenciaSoat() != null) {
            vehiculo.setVigenciaSoat(datos.getVigenciaSoat());
        }
        if (datos.getVigenciaRtm() != null) {
            vehiculo.setVigenciaRtm(datos.getVigenciaRtm());
        }
        if (datos.getFechaIngreso() != null) {
            vehiculo.setFechaIngreso(datos.getFechaIngreso());
        }
        vehiculo.setUltimaFechaActualizacion(LocalDateTime.now());
        return vehiculoRepository.save(vehiculo);
    }

    private Conductor obtenerConductorDisponible(String identificacion, Long vehiculoActualId) {
        Conductor conductor = conductorRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el conductor indicado."));
        if (!Boolean.TRUE.equals(conductor.getActivo())) {
            throw new BusinessException("El conductor indicado no está activo.");
        }
        vehiculoRepository.findAll().stream()
                .filter(vehiculo -> vehiculo.getConductor().getIdentificacion().equals(identificacion))
                .filter(vehiculo -> vehiculoActualId == null || !vehiculo.getId().equals(vehiculoActualId))
                .findAny()
                .ifPresent(vehiculo -> {
                    throw new BusinessException("El conductor ya está asignado a otro vehículo.");
                });
        return conductor;
    }

    private Tarifa obtenerTarifaActiva(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tarifa indicada."));
        if (!Boolean.TRUE.equals(tarifa.getActivo())) {
            throw new BusinessException("La tarifa indicada no está activa.");
        }
        return tarifa;
    }
}

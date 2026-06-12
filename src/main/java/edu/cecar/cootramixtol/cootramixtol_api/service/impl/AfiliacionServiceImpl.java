package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Afiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoAfiliacion;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.AfiliacionRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.AsociadoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.VehiculoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.AfiliacionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AfiliacionServiceImpl implements AfiliacionService {

    private final AfiliacionRepository afiliacionRepository;
    private final AsociadoRepository asociadoRepository;
    private final VehiculoRepository vehiculoRepository;

    public AfiliacionServiceImpl(AfiliacionRepository afiliacionRepository, AsociadoRepository asociadoRepository, VehiculoRepository vehiculoRepository) {
        this.afiliacionRepository = afiliacionRepository;
        this.asociadoRepository = asociadoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    @Transactional
    public Afiliacion registrar(Afiliacion afiliacion, String asociadoIdentificacion, String vehiculoPlaca) {
        Asociado asociado = asociadoRepository.findByIdentificacion(asociadoIdentificacion)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el asociado indicado."));
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(vehiculoPlaca)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el vehículo indicado."));
        if (!Boolean.TRUE.equals(asociado.getActivo())) {
            throw new BusinessException("El asociado indicado no está activo.");
        }
        if (!Boolean.TRUE.equals(vehiculo.getActivo())) {
            throw new BusinessException("El vehículo indicado no está activo.");
        }
        if (EstadoAfiliacion.ACTIVA.equals(afiliacion.getEstado()) && afiliacionRepository.existsByVehiculoPlacaAndEstado(vehiculoPlaca, EstadoAfiliacion.ACTIVA)) {
            throw new BusinessException("El vehículo ya tiene una afiliación activa.");
        }
        LocalDateTime now = LocalDateTime.now();
        afiliacion.setAsociado(asociado);
        afiliacion.setVehiculo(vehiculo);
        afiliacion.setFechaRegistro(now);
        afiliacion.setUltimaFechaActualizacion(now);
        return afiliacionRepository.save(afiliacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Afiliacion buscarPorId(Long id) {
        return afiliacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la afiliación indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Afiliacion> filtrar(String asociadoIdentificacion, String vehiculoPlaca, EstadoAfiliacion estado, LocalDate fechaAfiliacion, LocalDate fechaFinAfiliacion) {
        if (asociadoIdentificacion != null) {
            return afiliacionRepository.findByAsociadoIdentificacion(asociadoIdentificacion);
        }
        if (vehiculoPlaca != null) {
            return afiliacionRepository.findByVehiculoPlaca(vehiculoPlaca);
        }
        if (estado != null) {
            return afiliacionRepository.findByEstado(estado);
        }
        if (fechaAfiliacion != null) {
            return afiliacionRepository.findByFechaAfiliacion(fechaAfiliacion);
        }
        if (fechaFinAfiliacion != null) {
            return afiliacionRepository.findByFechaFinAfiliacion(fechaFinAfiliacion);
        }
        return afiliacionRepository.findAll();
    }

    @Override
    @Transactional
    public Afiliacion desafiliar(Long id, LocalDate fechaFinAfiliacion) {
        Afiliacion afiliacion = buscarPorId(id);
        if (!EstadoAfiliacion.ACTIVA.equals(afiliacion.getEstado())) {
            throw new BusinessException("Solo se puede desafiliar una afiliación activa.");
        }
        afiliacion.setFechaFinAfiliacion(fechaFinAfiliacion != null ? fechaFinAfiliacion : LocalDate.now());
        afiliacion.setEstado(EstadoAfiliacion.FINALIZADA);
        afiliacion.setUltimaFechaActualizacion(LocalDateTime.now());
        return afiliacionRepository.save(afiliacion);
    }
}

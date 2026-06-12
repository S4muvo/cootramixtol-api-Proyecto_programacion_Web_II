package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Consecutivo;
import edu.cecar.cootramixtol.cootramixtol_api.enums.EstadoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Planilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import edu.cecar.cootramixtol.cootramixtol_api.enums.TipoPlanilla;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.ConductorRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.ConsecutivoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.PlanillaRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.TarifaRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.VehiculoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.PlanillaService;
import edu.cecar.cootramixtol.cootramixtol_api.service.TarifaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanillaServiceImpl implements PlanillaService {

    private static final String CONSECUTIVO_PLANILLA = "PLANILLA";

    private final PlanillaRepository planillaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ConductorRepository conductorRepository;
    private final TarifaRepository tarifaRepository;
    private final ConsecutivoRepository consecutivoRepository;
    private final TarifaService tarifaService;

    public PlanillaServiceImpl(PlanillaRepository planillaRepository, VehiculoRepository vehiculoRepository, ConductorRepository conductorRepository, TarifaRepository tarifaRepository, ConsecutivoRepository consecutivoRepository, TarifaService tarifaService) {
        this.planillaRepository = planillaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.conductorRepository = conductorRepository;
        this.tarifaRepository = tarifaRepository;
        this.consecutivoRepository = consecutivoRepository;
        this.tarifaService = tarifaService;
    }

    @Override
    @Transactional
    public Planilla registrar(LocalDate fecha, LocalTime hora, String vehiculoPlaca, Long tarifaId, TipoPlanilla tipoPlanilla, String registradoPor) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(vehiculoPlaca)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el vehículo indicado."));
        validarDocumentos(fecha, vehiculo, vehiculo.getConductor());
        Tarifa tarifa = obtenerTarifa(tipoPlanilla, tarifaId, vehiculo);
        LocalDateTime now = LocalDateTime.now();
        Planilla planilla = new Planilla();
        planilla.setNumero(siguienteNumero());
        planilla.setFecha(fecha);
        planilla.setHora(hora != null ? hora : LocalTime.now());
        planilla.setVehiculo(vehiculo);
        planilla.setConductor(vehiculo.getConductor());
        planilla.setTarifa(tarifa);
        planilla.setTipoPlanilla(tipoPlanilla);
        planilla.setEstado(EstadoPlanilla.ACTIVA);
        planilla.setRegistradoPor(registradoPor);
        planilla.setFechaRegistro(now);
        planilla.setUltimaFechaActualizacion(now);
        return planillaRepository.save(planilla);
    }

    @Override
    @Transactional
    public List<Planilla> registrarMasivo(LocalDate fecha, List<String> placas, TipoPlanilla tipoPlanilla, String registradoPor) {
        List<Planilla> planillas = new ArrayList<>();
        for (String placa : placas) {
            planillas.add(registrar(fecha, LocalTime.now(), placa, null, tipoPlanilla, registradoPor));
        }
        return planillas;
    }

    @Override
    @Transactional(readOnly = true)
    public Planilla buscarPorNumero(Long numero) {
        return planillaRepository.findById(numero)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la planilla indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planilla> filtrar(LocalDate fecha, String vehiculoPlaca, EstadoPlanilla estado) {
        if (fecha != null) {
            return planillaRepository.findByFecha(fecha);
        }
        if (vehiculoPlaca != null) {
            return planillaRepository.findByVehiculoPlaca(vehiculoPlaca);
        }
        if (estado != null) {
            return planillaRepository.findByEstado(estado);
        }
        return planillaRepository.findAll();
    }

    @Override
    @Transactional
    public Planilla actualizar(Long numero, String conductorIdentificacion, Long tarifaId, EstadoPlanilla estado) {
        Planilla planilla = buscarPorNumero(numero);
        if (conductorIdentificacion != null) {
            Conductor conductor = conductorRepository.findByIdentificacion(conductorIdentificacion)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe el conductor indicado."));
            if (!Boolean.TRUE.equals(conductor.getActivo())) {
                throw new BusinessException("El conductor indicado no está activo.");
            }
            if (conductor.getVigenciaLicencia().isBefore(planilla.getFecha())) {
                throw new BusinessException("La licencia del conductor se encuentra vencida para la fecha de la planilla.");
            }
            planilla.setConductor(conductor);
        }
        if (tarifaId != null) {
            Tarifa tarifa = tarifaRepository.findById(tarifaId)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe la tarifa indicada."));
            if (!Boolean.TRUE.equals(tarifa.getActivo())) {
                throw new BusinessException("La tarifa indicada no está activa.");
            }
            planilla.setTarifa(tarifa);
        }
        if (estado != null) {
            planilla.setEstado(estado);
        }
        planilla.setUltimaFechaActualizacion(LocalDateTime.now());
        return planillaRepository.save(planilla);
    }

    private void validarDocumentos(LocalDate fecha, Vehiculo vehiculo, Conductor conductor) {
        if (!Boolean.TRUE.equals(vehiculo.getActivo())) {
            throw new BusinessException("El vehículo no está activo.");
        }
        if (!Boolean.TRUE.equals(conductor.getActivo())) {
            throw new BusinessException("El conductor asignado no está activo.");
        }
        if (vehiculo.getVigenciaSoat().isBefore(fecha)) {
            throw new BusinessException("El SOAT del vehículo está vencido.");
        }
        if (vehiculo.getVigenciaRtm().isBefore(fecha)) {
            throw new BusinessException("La revisión técnico-mecánica del vehículo está vencida.");
        }
        if (conductor.getVigenciaLicencia().isBefore(fecha)) {
            throw new BusinessException("La licencia del conductor asignado está vencida.");
        }
    }

    private Tarifa obtenerTarifa(TipoPlanilla tipoPlanilla, Long tarifaId, Vehiculo vehiculo) {
        if (TipoPlanilla.TALLER.equals(tipoPlanilla)) {
            return tarifaService.buscarTarifaCero();
        }
        if (tarifaId != null) {
            Tarifa tarifa = tarifaRepository.findById(tarifaId)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe la tarifa indicada."));
            if (!Boolean.TRUE.equals(tarifa.getActivo())) {
                throw new BusinessException("La tarifa indicada no está activa.");
            }
            return tarifa;
        }
        if (!Boolean.TRUE.equals(vehiculo.getTarifa().getActivo())) {
            throw new BusinessException("La tarifa del vehículo no está activa.");
        }
        return vehiculo.getTarifa();
    }

    private Long siguienteNumero() {
        Consecutivo consecutivo = consecutivoRepository.findWithLockByNombre(CONSECUTIVO_PLANILLA)
                .orElseGet(() -> {
                    Consecutivo nuevo = new Consecutivo();
                    nuevo.setNombre(CONSECUTIVO_PLANILLA);
                    nuevo.setValor(0L);
                    return nuevo;
                });
        Long siguiente = consecutivo.getValor() + 1;
        consecutivo.setValor(siguiente);
        consecutivoRepository.save(consecutivo);
        return siguiente;
    }
}

package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Despacho;
import edu.cecar.cootramixtol.cootramixtol_api.entity.Vehiculo;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.DespachoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.repository.VehiculoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.DespachoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DespachoServiceImpl implements DespachoService {

    private final DespachoRepository despachoRepository;
    private final VehiculoRepository vehiculoRepository;

    public DespachoServiceImpl(DespachoRepository despachoRepository, VehiculoRepository vehiculoRepository) {
        this.despachoRepository = despachoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    @Transactional
    public Despacho registrar(Despacho despacho, String vehiculoPlaca) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(vehiculoPlaca)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el vehículo indicado."));
        if (!Boolean.TRUE.equals(vehiculo.getActivo())) {
            throw new BusinessException("El vehículo no está activo.");
        }
        if (despacho.getNumeroPasajeros() > vehiculo.getCapacidadPasajeros()) {
            throw new BusinessException("El número de pasajeros supera la capacidad del vehículo.");
        }
        despacho.setVehiculo(vehiculo);
        despacho.setFechaRegistro(LocalDateTime.now());
        return despachoRepository.save(despacho);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Despacho> filtrar(LocalDate fecha, String vehiculoPlaca, String ruta) {
        if (fecha != null) {
            return despachoRepository.findByFecha(fecha);
        }
        if (vehiculoPlaca != null) {
            return despachoRepository.findByVehiculoPlaca(vehiculoPlaca);
        }
        if (ruta != null) {
            return despachoRepository.findByRutaContainingIgnoreCase(ruta);
        }
        return despachoRepository.findAll();
    }
}

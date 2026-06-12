package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Tarifa;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.TarifaRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.TarifaService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarifaServiceImpl implements TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaServiceImpl(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    @Transactional
    public Tarifa registrar(Tarifa tarifa) {
        tarifa.setTotal(tarifa.getAportesGastosVarios().add(tarifa.getFondoReposicion()).add(tarifa.getAporteSocialPorSocio()));
        LocalDateTime now = LocalDateTime.now();
        tarifa.setFechaRegistro(now);
        tarifa.setUltimaFechaActualizacion(now);
        return tarifaRepository.save(tarifa);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarifa buscarPorId(Long id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tarifa indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarifa> filtrar(Boolean activo) {
        if (activo != null) {
            return tarifaRepository.findByActivo(activo);
        }
        return tarifaRepository.findAll();
    }

    @Override
    @Transactional
    public Tarifa inactivar(Long id) {
        Tarifa tarifa = buscarPorId(id);
        tarifa.setActivo(false);
        tarifa.setUltimaFechaActualizacion(LocalDateTime.now());
        return tarifaRepository.save(tarifa);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarifa buscarTarifaCero() {
        return tarifaRepository.findByActivo(true).stream()
                .filter(tarifa -> tarifa.getTotal().compareTo(BigDecimal.ZERO) == 0)
                .findFirst()
                .orElseThrow(() -> new BusinessException("Debe existir una tarifa activa de cero pesos para vehículos en taller."));
    }
}

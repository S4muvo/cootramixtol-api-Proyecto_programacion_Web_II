package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Asociado;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.AsociadoRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.AsociadoService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsociadoServiceImpl implements AsociadoService {

    private final AsociadoRepository asociadoRepository;

    public AsociadoServiceImpl(AsociadoRepository asociadoRepository) {
        this.asociadoRepository = asociadoRepository;
    }

    @Override
    @Transactional
    public Asociado registrar(Asociado asociado) {
        validarNuevo(asociado);
        LocalDateTime now = LocalDateTime.now();
        asociado.setFechaRegistro(now);
        asociado.setUltimaFechaActualizacion(now);
        return asociadoRepository.save(asociado);
    }

    @Override
    @Transactional(readOnly = true)
    public Asociado buscarPorIdentificacion(String identificacion) {
        return asociadoRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un asociado con la identificación indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asociado> filtrar(String nombres, Boolean activo) {
        if (nombres != null && activo != null) {
            return asociadoRepository.findByNombresContainingIgnoreCaseAndActivo(nombres, activo);
        }
        if (nombres != null) {
            return asociadoRepository.findByNombresContainingIgnoreCase(nombres);
        }
        if (activo != null) {
            return asociadoRepository.findByActivo(activo);
        }
        return asociadoRepository.findAll();
    }

    @Override
    @Transactional
    public Asociado actualizar(String identificacion, Asociado datos) {
        Asociado asociado = buscarPorIdentificacion(identificacion);
        validarUnicosActualizacion(asociado, datos);
        if (datos.getNombres() != null) {
            asociado.setNombres(datos.getNombres());
        }
        if (datos.getActivo() != null) {
            asociado.setActivo(datos.getActivo());
        }
        if (datos.getCelular() != null) {
            asociado.setCelular(datos.getCelular());
        }
        if (datos.getCorreo() != null) {
            asociado.setCorreo(datos.getCorreo());
        }
        if (datos.getFechaNacimiento() != null) {
            asociado.setFechaNacimiento(datos.getFechaNacimiento());
        }
        if (datos.getFechaIngreso() != null) {
            asociado.setFechaIngreso(datos.getFechaIngreso());
        }
        asociado.setUltimaFechaActualizacion(LocalDateTime.now());
        return asociadoRepository.save(asociado);
    }

    private void validarNuevo(Asociado asociado) {
        if (asociadoRepository.existsByIdentificacion(asociado.getIdentificacion())) {
            throw new BusinessException("Ya existe un asociado con esa identificación.");
        }
        if (asociadoRepository.existsByCelular(asociado.getCelular())) {
            throw new BusinessException("Ya existe un asociado con ese celular.");
        }
        if (asociadoRepository.existsByCorreo(asociado.getCorreo())) {
            throw new BusinessException("Ya existe un asociado con ese correo.");
        }
    }

    private void validarUnicosActualizacion(Asociado actual, Asociado datos) {
        if (datos.getCelular() != null && !datos.getCelular().equals(actual.getCelular()) && asociadoRepository.existsByCelular(datos.getCelular())) {
            throw new BusinessException("Ya existe un asociado con ese celular.");
        }
        if (datos.getCorreo() != null && !datos.getCorreo().equals(actual.getCorreo()) && asociadoRepository.existsByCorreo(datos.getCorreo())) {
            throw new BusinessException("Ya existe un asociado con ese correo.");
        }
    }
}

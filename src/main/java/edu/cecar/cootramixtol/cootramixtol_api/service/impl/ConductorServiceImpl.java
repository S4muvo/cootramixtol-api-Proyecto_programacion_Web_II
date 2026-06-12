package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Conductor;
import edu.cecar.cootramixtol.cootramixtol_api.exception.BusinessException;
import edu.cecar.cootramixtol.cootramixtol_api.exception.ResourceNotFoundException;
import edu.cecar.cootramixtol.cootramixtol_api.repository.ConductorRepository;
import edu.cecar.cootramixtol.cootramixtol_api.service.ConductorService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConductorServiceImpl implements ConductorService {

    private final ConductorRepository conductorRepository;

    public ConductorServiceImpl(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @Override
    @Transactional
    public Conductor registrar(Conductor conductor) {
        validarNuevo(conductor);
        LocalDateTime now = LocalDateTime.now();
        conductor.setFechaRegistro(now);
        conductor.setUltimaFechaActualizacion(now);
        return conductorRepository.save(conductor);
    }

    @Override
    @Transactional(readOnly = true)
    public Conductor buscarPorIdentificacion(String identificacion) {
        return conductorRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un conductor con la identificación indicada."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conductor> filtrar(String nombres, Boolean activo) {
        if (nombres != null && activo != null) {
            return conductorRepository.findByNombresContainingIgnoreCaseAndActivo(nombres, activo);
        }
        if (nombres != null) {
            return conductorRepository.findByNombresContainingIgnoreCase(nombres);
        }
        if (activo != null) {
            return conductorRepository.findByActivo(activo);
        }
        return conductorRepository.findAll();
    }

    @Override
    @Transactional
    public Conductor actualizar(String identificacion, Conductor datos) {
        Conductor conductor = buscarPorIdentificacion(identificacion);
        validarUnicosActualizacion(conductor, datos);
        if (datos.getNombres() != null) {
            conductor.setNombres(datos.getNombres());
        }
        if (datos.getFechaNacimiento() != null) {
            conductor.setFechaNacimiento(datos.getFechaNacimiento());
        }
        if (datos.getActivo() != null) {
            conductor.setActivo(datos.getActivo());
        }
        if (datos.getNumeroLicencia() != null) {
            conductor.setNumeroLicencia(datos.getNumeroLicencia());
        }
        if (datos.getCategoriaLicencia() != null) {
            conductor.setCategoriaLicencia(datos.getCategoriaLicencia());
        }
        if (datos.getVigenciaLicencia() != null) {
            conductor.setVigenciaLicencia(datos.getVigenciaLicencia());
        }
        if (datos.getCelular() != null) {
            conductor.setCelular(datos.getCelular());
        }
        if (datos.getCorreo() != null) {
            conductor.setCorreo(datos.getCorreo());
        }
        conductor.setUltimaFechaActualizacion(LocalDateTime.now());
        return conductorRepository.save(conductor);
    }

    private void validarNuevo(Conductor conductor) {
        if (conductorRepository.existsByIdentificacion(conductor.getIdentificacion())) {
            throw new BusinessException("Ya existe un conductor con esa identificación.");
        }
        if (conductorRepository.existsByNumeroLicencia(conductor.getNumeroLicencia())) {
            throw new BusinessException("Ya existe un conductor con esa licencia.");
        }
        if (conductorRepository.existsByCelular(conductor.getCelular())) {
            throw new BusinessException("Ya existe un conductor con ese celular.");
        }
        if (conductorRepository.existsByCorreo(conductor.getCorreo())) {
            throw new BusinessException("Ya existe un conductor con ese correo.");
        }
    }

    private void validarUnicosActualizacion(Conductor actual, Conductor datos) {
        if (datos.getNumeroLicencia() != null && !datos.getNumeroLicencia().equals(actual.getNumeroLicencia()) && conductorRepository.existsByNumeroLicencia(datos.getNumeroLicencia())) {
            throw new BusinessException("Ya existe un conductor con esa licencia.");
        }
        if (datos.getCelular() != null && !datos.getCelular().equals(actual.getCelular()) && conductorRepository.existsByCelular(datos.getCelular())) {
            throw new BusinessException("Ya existe un conductor con ese celular.");
        }
        if (datos.getCorreo() != null && !datos.getCorreo().equals(actual.getCorreo()) && conductorRepository.existsByCorreo(datos.getCorreo())) {
            throw new BusinessException("Ya existe un conductor con ese correo.");
        }
    }
}

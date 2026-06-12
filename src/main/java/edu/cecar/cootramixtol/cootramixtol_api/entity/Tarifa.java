package edu.cecar.cootramixtol.cootramixtol_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal aportesGastosVarios;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal fondoReposicion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal aporteSocialPorSocio;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private LocalDateTime ultimaFechaActualizacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAportesGastosVarios() {
        return aportesGastosVarios;
    }

    public void setAportesGastosVarios(BigDecimal aportesGastosVarios) {
        this.aportesGastosVarios = aportesGastosVarios;
    }

    public BigDecimal getFondoReposicion() {
        return fondoReposicion;
    }

    public void setFondoReposicion(BigDecimal fondoReposicion) {
        this.fondoReposicion = fondoReposicion;
    }

    public BigDecimal getAporteSocialPorSocio() {
        return aporteSocialPorSocio;
    }

    public void setAporteSocialPorSocio(BigDecimal aporteSocialPorSocio) {
        this.aporteSocialPorSocio = aporteSocialPorSocio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getUltimaFechaActualizacion() {
        return ultimaFechaActualizacion;
    }

    public void setUltimaFechaActualizacion(LocalDateTime ultimaFechaActualizacion) {
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }
}

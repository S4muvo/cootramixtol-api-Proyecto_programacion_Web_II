package edu.cecar.cootramixtol.cootramixtol_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consecutivos")
public class Consecutivo {

    @Id
    @Column(length = 40)
    private String nombre;

    @Column(nullable = false)
    private Long valor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
}

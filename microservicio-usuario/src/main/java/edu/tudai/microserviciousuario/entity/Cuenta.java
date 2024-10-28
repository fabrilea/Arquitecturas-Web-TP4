package edu.tudai.microserviciousuario.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaAlta;
    private Double saldo;

    @ManyToMany
    private List<Usuario> usuarios;

    public Cuenta() {
        super();
        this.usuarios = new ArrayList<Usuario>();
    }

    public Cuenta(String fechaAlta, Double saldo) {
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}

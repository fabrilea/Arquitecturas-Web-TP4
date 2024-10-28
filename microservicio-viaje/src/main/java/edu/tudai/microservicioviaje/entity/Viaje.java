package edu.tudai.microservicioviaje.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private Long cuentaId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double kilometrosRecorridos;
    private boolean enCurso;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("viaje")
    private List<Pausa> pausas;

    public Viaje() {
        super();
        this.pausas = new ArrayList<>();
    }

    public Viaje(Long usuarioId, Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin, double kilometrosRecorridos, boolean enCurso) {
        super();
        this.usuarioId = usuarioId;
        this.cuentaId = cuentaId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.kilometrosRecorridos = kilometrosRecorridos;
        this.enCurso = enCurso;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public void setEnCurso(boolean enCurso) {
        this.enCurso = enCurso;
    }

    public List<Pausa> getPausas() {
        return pausas;
    }
}

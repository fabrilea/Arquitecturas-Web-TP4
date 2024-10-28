package edu.tudai.microserviciofactura.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private double montoTotal;
    private LocalDate fechaEmision;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detallesFactura;

    public Factura() {
        super();
        this.detallesFactura = new ArrayList<DetalleFactura>();
    }

    public Factura(Long usuarioId, double montoTotal, LocalDate fechaEmision) {
        super();
        this.usuarioId = usuarioId;
        this.montoTotal = montoTotal;
        this.fechaEmision = fechaEmision;
    }

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

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleFactura> getDetallesFactura() {
        return detallesFactura;
    }

    public double calcularMontoTotal() {
        return detallesFactura.stream()
                .mapToDouble(DetalleFactura::getMontoCalculado)
                .sum();
    }
}

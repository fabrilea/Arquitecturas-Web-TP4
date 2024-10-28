package edu.tudai.microserviciofactura.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Tarifa {

    public enum TipoTarifa {
        BASE,
        EXTRA_PAUSA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTarifa tipo;

    private double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Constructor, Getters y Setters
}

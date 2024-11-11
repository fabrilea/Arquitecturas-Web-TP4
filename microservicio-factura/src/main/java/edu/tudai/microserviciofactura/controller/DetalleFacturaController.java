package edu.tudai.microserviciofactura.controller;

import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.service.DetalleFacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/detallefactura")
public class DetalleFacturaController {

    private final DetalleFacturaService detalleFacturaService;

    @GetMapping
    public ResponseEntity<List<DetalleFactura>> getAllDetallesFactura() {
        List<DetalleFactura> detalleFacturas = detalleFacturaService.findAll();
        if (detalleFacturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalleFacturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> getDetalleFacturaById(@PathVariable("id") Long id) {
        DetalleFactura detalleFactura = detalleFacturaService.findById(id);
        if (detalleFactura == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(detalleFactura);
    }

    @PostMapping
    public ResponseEntity<DetalleFactura> createDetalleFactura(@RequestBody DetalleFactura monopatin) {
        DetalleFactura detalleFacturaCreated = detalleFacturaService.save(monopatin);
        if (detalleFacturaCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(detalleFacturaCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleFactura(@PathVariable("id") Long id) {
        detalleFacturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleFactura> updateDetalleFactura(@PathVariable("id") Long id, @RequestBody DetalleFactura detalleFactura) {
        DetalleFactura detalleFacturaExistente = detalleFacturaService.findById(id);

        if (detalleFacturaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        detalleFacturaExistente.setFactura(detalleFactura.getFactura());
        detalleFacturaExistente.setTiempoUso(detalleFactura.getTiempoUso());
        detalleFacturaExistente.setTiempoPausado(detalleFactura.getTiempoPausado());
        detalleFacturaExistente.setTarifaBase(detalleFactura.getTarifaBase());
        detalleFacturaExistente.setTarifaExtra(detalleFactura.getTarifaExtra());
        detalleFacturaExistente.setViajeId(detalleFactura.getViajeId());

        DetalleFactura detalleFacturaUpdated = detalleFacturaService.update(detalleFacturaExistente);

        return ResponseEntity.ok(detalleFacturaUpdated);
    }
}

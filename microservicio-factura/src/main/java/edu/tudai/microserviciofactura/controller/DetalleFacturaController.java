package edu.tudai.microserviciofactura.controller;

import edu.tudai.microserviciofactura.dto.DetalleFacturaDTO;
import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.repository.FacturaRepository;
import edu.tudai.microserviciofactura.service.DetalleFacturaService;
import edu.tudai.microserviciofactura.service.FacturaService;
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
    private final FacturaService facturaService;

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
    public ResponseEntity<DetalleFactura> createDetalleFactura(@RequestBody DetalleFacturaDTO detalleDTO) {
        Factura factura = facturaService.findById(detalleDTO.getFacturaId());

        DetalleFactura detalle = new DetalleFactura(factura, detalleDTO.getViajeId(),
                detalleDTO.getTarifaBase(), detalleDTO.getTarifaExtra(),
                detalleDTO.getTiempoUso(), detalleDTO.getTiempoPausado());

        detalle.calcularMonto();  // Calcular el monto antes de guardar
        detalleFacturaService.save(detalle);
        return ResponseEntity.ok(detalle);
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

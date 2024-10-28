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
    public ResponseEntity<List<DetalleFactura>> getAllDetallesFacturas() {
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


}

package edu.tudai.microservicioviaje.controller;

import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.service.PausaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pausa")
public class PausaController {

    private final PausaService pausaService;

    @GetMapping
    public ResponseEntity<List<Pausa>> getAllPausas() {
        List<Pausa> pausas = pausaService.findAll();
        if (pausas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pausas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pausa> getPausaById(@PathVariable Long id) {
        Pausa pausa = pausaService.findById(id);
        return pausa != null ? ResponseEntity.ok(pausa) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Pausa> createPausa(@RequestBody Pausa pausa) {
        Pausa createdPausa = pausaService.save(pausa);
        return ResponseEntity.ok(createdPausa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pausa> updatePausa(@PathVariable Long id, @RequestBody Pausa pausa) {
        Pausa updatedPausa = pausaService.update(id, pausa);
        return updatedPausa != null ? ResponseEntity.ok(updatedPausa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePausa(@PathVariable Long id) {
        pausaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

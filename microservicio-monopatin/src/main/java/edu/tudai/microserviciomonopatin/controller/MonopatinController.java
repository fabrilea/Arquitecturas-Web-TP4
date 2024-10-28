package edu.tudai.microserviciomonopatin.controller;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.service.MonopatinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/monopatin")
public class MonopatinController {

    private final MonopatinService monopatinService;


    @GetMapping
    public ResponseEntity<List<Monopatin>> getAllMonopatines() {
        List<Monopatin> monopatines = monopatinService.findAll();
        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> getMonopatinById(@PathVariable("id") Long id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(monopatin);
    }

    @PostMapping
    public ResponseEntity<Monopatin> createMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin monopatinCreated = monopatinService.save(monopatin);
        if (monopatinCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(monopatinCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonopatin(@PathVariable("id") Long id) {
        monopatinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> updateMonopatin(@PathVariable("id") Long id, @RequestBody Monopatin monopatin) {
        Monopatin monopatinExistente = monopatinService.findById(id);

        if (monopatinExistente == null) {
            return ResponseEntity.notFound().build();
        }

        monopatinExistente.setBateria(monopatin.getBateria());
        monopatinExistente.setLatitud(monopatin.getLatitud());
        monopatinExistente.setLongitud(monopatin.getLongitud());
        monopatinExistente.setTiempoUso(monopatin.getTiempoUso());
        monopatinExistente.setKilometrosRecorridos(monopatin.getKilometrosRecorridos());
        monopatinExistente.setDisponible(monopatin.isDisponible());
        monopatinExistente.setParada(monopatin.getParada());

        Monopatin monopatinUpdated = monopatinService.update(monopatinExistente);

        return ResponseEntity.ok(monopatinUpdated);
    }

    /********************************************************************/
}
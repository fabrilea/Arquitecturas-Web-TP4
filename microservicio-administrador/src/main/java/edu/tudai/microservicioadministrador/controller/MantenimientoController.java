package edu.tudai.microservicioadministrador.controller;

import edu.tudai.microservicioadministrador.entity.Mantenimiento;
import edu.tudai.microservicioadministrador.service.MantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/mantenimiento")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<List<Mantenimiento>> getAllMantenimientos() {
        List<Mantenimiento> mantenimientos = mantenimientoService.findAll();
        if (mantenimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mantenimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> getMantenimientoById(@PathVariable("id") Long id) {
        Mantenimiento mantenimiento = mantenimientoService.findById(id);
        if (mantenimiento == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mantenimiento);
    }

    @PostMapping
    public ResponseEntity<Mantenimiento> createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        Mantenimiento mantenimientoCreated = mantenimientoService.save(mantenimiento);
        if (mantenimientoCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mantenimientoCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMantenimiento(@PathVariable("id") Long id) {
        mantenimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mantenimiento> updatemantenimiento(@PathVariable("id") Long id, @RequestBody Mantenimiento mantenimiento) {
        Mantenimiento mantenimientoExistente = mantenimientoService.findById(id);

        if (mantenimientoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        mantenimientoExistente.setDescripcion(mantenimiento.getDescripcion());
        mantenimientoExistente.setMonopatinId(mantenimiento.getMonopatinId());
        mantenimientoExistente.setFechaInicio(mantenimiento.getFechaInicio());
        mantenimientoExistente.setFechaFin(mantenimiento.getFechaFin());

        Mantenimiento mantenimientoUpdated = mantenimientoService.update(mantenimientoExistente);

        return ResponseEntity.ok(mantenimientoUpdated);
    }

    /*****************************************************/

    @PostMapping("/iniciar/{monopatinId}")
    public ResponseEntity<Mantenimiento> iniciarMantenimiento(
            @PathVariable Long monopatinId,
            @RequestBody(required = false) String descripcion) {
        Mantenimiento mantenimiento = mantenimientoService.iniciarMantenimiento(monopatinId, descripcion);
        return ResponseEntity.ok(mantenimiento);
    }

    @PutMapping("/finalizar/{monopatinId}")
    public ResponseEntity<Mantenimiento> finalizarMantenimiento(@PathVariable Long monopatinId) {
        Mantenimiento mantenimiento = mantenimientoService.finalizarMantenimiento(monopatinId);
        return ResponseEntity.ok(mantenimiento);
    }
}

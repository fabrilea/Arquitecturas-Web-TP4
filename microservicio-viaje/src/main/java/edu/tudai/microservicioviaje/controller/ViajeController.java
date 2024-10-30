package edu.tudai.microservicioviaje.controller;

import edu.tudai.microservicioviaje.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/viaje")
public class ViajeController {

    private final ViajeService viajeService;

    @GetMapping
    public ResponseEntity<List<Viaje>> getAllViajes(){
        List<Viaje> viajes = viajeService.findAll();
        if(viajes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViaje(@PathVariable("id") Long id){
        Viaje viaje = viajeService.findById(id);
        if(viaje == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PostMapping
    public ResponseEntity<Viaje> createViaje(@RequestBody Viaje viaje){
        Viaje viajeCreated = viajeService.save(viaje);
        if(viajeCreated == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajeCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Viaje> deleteViaje(@PathVariable("id") Long id){
        viajeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable("id") Long id, @RequestBody Viaje viaje){
        Viaje viajeExistente = viajeService.findById(id);
        if(viajeExistente == null){
            return ResponseEntity.noContent().build();
        }

        viajeExistente.setFechaInicio(viaje.getFechaInicio());
        viajeExistente.setFechaFin(viaje.getFechaFin());
        viajeExistente.setMonopatinId(viaje.getMonopatinId());
        viajeExistente.setEnCurso(viaje.isEnCurso());
        viajeExistente.setKilometrosRecorridos(viaje.getKilometrosRecorridos());

        Viaje viajeUpdated = viajeService.save(viajeExistente);
        return ResponseEntity.ok(viajeUpdated);
    }


    /****************************************************/

    @GetMapping("/{viajeId}/tiempo-total-con-pausas")
    public ResponseEntity<Double> obtenerTiempoTotalConPausas(@PathVariable("viajeId") Long viajeId) {
        Double tiempoTotalConPausas = viajeService.calcularTiempoTotalConPausas(viajeId);
        return ResponseEntity.ok(tiempoTotalConPausas);
    }

    @PutMapping("/{viajeId}/finalizar")
    public ResponseEntity<Viaje> finalizarViaje(@PathVariable Long viajeId, @RequestParam double kilometrosRecorridos) {
        try {
            Viaje viajeFinalizado = viajeService.finalizarViaje(viajeId, kilometrosRecorridos);
            return ResponseEntity.ok(viajeFinalizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/agregarPausa/{id}")
    public ResponseEntity<Viaje> agregarPausa(@PathVariable("id") Long id, @RequestBody Pausa pausa) {
        Viaje viajeExistente = viajeService.findById(id);
        if (viajeExistente == null) {
            return ResponseEntity.noContent().build();
        }

        pausa.setViaje(viajeExistente);  // Asociar la pausa al viaje existente
        viajeExistente.getPausas().add(pausa);
        viajeService.save(viajeExistente);  // Guardar el viaje actualizado con la nueva pausa
        return ResponseEntity.ok(viajeExistente);
    }

    @GetMapping("/reportes/kilometros")
    public ResponseEntity<List<ReporteKilometrosDTO>> obtenerReporteKilometros() {
        List<ReporteKilometrosDTO> reporte = viajeService.generarReporteKilometros();
        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }
}

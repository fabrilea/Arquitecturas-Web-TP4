package edu.tudai.microserviciomonopatin.controller;

import edu.tudai.microserviciomonopatin.entity.Parada;
import edu.tudai.microserviciomonopatin.service.ParadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/parada")
public class ParadaController {

    private final ParadaService paradaService;

    @GetMapping
    public ResponseEntity<List<Parada>> getAllParadas(){
        List<Parada> paradas = paradaService.findAll();
        if(paradas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable("id") Long id){
        Parada parada = paradaService.findById(id);
        if(parada == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parada);
    }

    @PostMapping
    public ResponseEntity<Parada> createParada(@RequestBody Parada parada){
        Parada paradaCreated = paradaService.save(parada);
        if(paradaCreated == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradaCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Parada> deleteParada(@PathVariable("id") Long id){
        paradaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parada> updateParada(@PathVariable("id") Long id, @RequestBody Parada parada){
        Parada paradaExistente = paradaService.findById(id);
        if(paradaExistente == null){
            return ResponseEntity.noContent().build();
        }

        //paradaExistente.setDireccion(parada.getDireccion());
        paradaExistente.setNombre(parada.getNombre());
        paradaExistente.setLatitud(parada.getLatitud());
        paradaExistente.setLongitud(parada.getLongitud());

        Parada paradaUpdated = paradaService.update(paradaExistente);

        return ResponseEntity.ok(paradaUpdated);
    }
}

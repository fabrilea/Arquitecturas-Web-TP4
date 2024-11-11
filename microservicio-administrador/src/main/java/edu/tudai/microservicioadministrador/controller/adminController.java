package edu.tudai.microservicioadministrador.controller;

import edu.tudai.microservicioadministrador.entity.Monopatin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import  edu.tudai.microservicioadministrador.service.adminService;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class adminController {

    private final adminService adminService;

    @PutMapping("/anularCuenta/{id}")
    public ResponseEntity<Void> anularCuenta(@PathVariable("id") long id) {
        adminService.anularCuenta(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/monopatinViajes/{minViajes}/{anio}")
    public ResponseEntity<List<Monopatin>> obtenerMonopatinesConMasViajes(
            @RequestParam int minViajes, @RequestParam int anio) {
        List<Monopatin> result = adminService.obtenerMonopatinesConMasViajes(minViajes, anio);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/monopatines/comparacion")
    public ResponseEntity<Map<String, Long>> monopatinesVS(){
        Map<String, Long>  result = adminService.getVSMonopatin();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/factura/total-facturado")
    public ResponseEntity<Double> obtenerTotalFacturado(
            @RequestParam int anio, @RequestParam int mesInicio, @RequestParam int mesFin) {
        double total = adminService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/tarifa/ajustar-precios")
    public ResponseEntity<Void> ajustarPrecios(
            @RequestParam double nuevaTarifaBase,
            @RequestParam double nuevaTarifaExtra,
            @RequestParam LocalDate fechaInicio) {
        adminService.ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);
        return ResponseEntity.ok().build();
    }

}

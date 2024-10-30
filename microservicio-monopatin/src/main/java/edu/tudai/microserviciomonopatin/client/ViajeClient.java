package edu.tudai.microserviciomonopatin.client;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "viaje-service", url = "http://localhost:8003/api/viaje")
public interface ViajeClient {

    @GetMapping("/monopatines-con-mas-viajes")
    List<Monopatin> obtenerMonopatinesConMasViajes(
            @RequestParam("minViajes") int minViajes,
            @RequestParam("anio") int anio);
}
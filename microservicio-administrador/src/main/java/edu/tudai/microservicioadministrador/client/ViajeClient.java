package edu.tudai.microservicioadministrador.client;

import edu.tudai.microservicioadministrador.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "viaje-service", url = "http://localhost:8003/api/viaje")
public interface ViajeClient {
    @GetMapping("")
    List<ViajeDTO> getAllViajes();
}

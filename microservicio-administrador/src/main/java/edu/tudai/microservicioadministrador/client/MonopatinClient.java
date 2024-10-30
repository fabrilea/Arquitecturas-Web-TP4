package edu.tudai.microservicioadministrador.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "monopatin-service", url = "http://localhost:8002/api/monopatin")
public interface MonopatinClient {

    @PutMapping("/{id}/disponibilidad")
    void actualizarDisponibilidad(@PathVariable("id") Long id, @RequestParam("disponible") boolean disponible);

    @PutMapping("/{id}/mantenimiento")
    void actualizarEnMantenimiento(@PathVariable("id") Long id, @RequestParam("enMantenimiento") boolean disponible);
}

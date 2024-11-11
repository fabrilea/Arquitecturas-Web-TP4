package edu.tudai.microserviciousuario.client;


import edu.tudai.microserviciousuario.entity.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8002/api/monopatin")
public interface monopatinClient {

    @GetMapping("/cercanos")
    List<Monopatin> obtenerMonopatinesCercanos(
            @RequestParam("latitud") double latitud,
            @RequestParam("longitud") double longitud,
            @RequestParam("radio") double radio
    );

}

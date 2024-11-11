package edu.tudai.microserviciousuario.client;


import edu.tudai.microserviciousuario.entity.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8002")
@RequestMapping("/api/monopatin")
public interface monopatinClient {

    @GetMapping("/cercanos")
    List<Monopatin> obtenerMonopatinesCercanos(
            @Param("latitud") double latitud,
            @Param("longitud") double longitud,
            @Param("radio") double radio
    );

}

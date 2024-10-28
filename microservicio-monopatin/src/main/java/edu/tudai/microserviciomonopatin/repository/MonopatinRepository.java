package edu.tudai.microserviciomonopatin.repository;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query("SELECT new edu.tudai.microserviciomonopatin.dto.MonopatinReporteDTO(m.id, SUM(m.kilometrosRecorridos), m.tiempoUso) " +
            "FROM Monopatin m GROUP BY m.id")
    List<MonopatinReporteDTO> reporteKilometrosRecorridos();

    @Query("SELECT new edu.tudai.microserviciomonopatin.dto.MonopatinReporteDTO(m.id, null, SUM(m.tiempoUso)) " +
            "FROM Monopatin m GROUP BY m.id")
    List<MonopatinReporteDTO> reporteTiempoSinPausas();
}

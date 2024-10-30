package edu.tudai.microservicioviaje.service;

import edu.tudai.microservicioviaje.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    @Transactional(readOnly = true)
    public List<Viaje> findAll() {
        return viajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @Transactional
    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    @Transactional
    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    /****************************************/

    @Transactional(readOnly = true)
    public Double calcularTiempoTotalConPausas(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        double totalPausaMinutos = 0;

        for (Pausa pausa : viaje.getPausas()) {
            if (pausa.getFin() != null) { // Ignorar pausas en curso
                long minutos = Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes();
                totalPausaMinutos += minutos;
            }
        }

        // Retornar tiempo total incluyendo pausas
        return viaje.getTiempoUso() + totalPausaMinutos;
    }

    @Transactional
    public Viaje finalizarViaje(Long viajeId, double kilometrosRecorridos) {
        // Buscar el viaje por ID
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Verificar si el viaje ya está finalizado
        if (!viaje.isEnCurso()) {
            throw new RuntimeException("El viaje ya ha sido finalizado");
        }

        // Establecer la fecha y hora de finalización
        viaje.setFechaFin(LocalDateTime.now());
        viaje.setKilometrosRecorridos(kilometrosRecorridos);
        viaje.setEnCurso(false);

        // Guardar cambios
        viajeRepository.save(viaje);

        return viaje;
    }


    public List<ReporteKilometrosDTO> generarReporteKilometros() {
        // Consultar todos los viajes y agrupar por monopatinId, sumando los kilómetros
        Map<Long, Double> kilometrosPorMonopatin = new HashMap<>();

        List<Viaje> viajes = viajeRepository.findAll();

        for (Viaje viaje : viajes) {
            kilometrosPorMonopatin.merge(viaje.getMonopatinId(), viaje.getKilometrosRecorridos(), Double::sum);
        }

        // Convertir el mapa a una lista de DTOs
        List<ReporteKilometrosDTO> reportes = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : kilometrosPorMonopatin.entrySet()) {
            reportes.add(new ReporteKilometrosDTO(entry.getKey(), entry.getValue()));
        }

        return reportes;
    }
}

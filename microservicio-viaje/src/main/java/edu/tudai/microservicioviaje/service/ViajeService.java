package edu.tudai.microservicioviaje.service;

import edu.tudai.microservicioviaje.dto.ViajeDTO;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<ViajeDTO> obtenerViajesPorMonopatin(Long monopatinId) {
        List<Viaje> viajes = viajeRepository.findByMonopatinId(monopatinId);

        return viajes.stream().map(viaje -> {
            double totalPausaMinutos = viaje.getPausas().stream()
                    .mapToDouble(pausa -> Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes())
                    .sum();

            return new ViajeDTO(viaje.getId(), viaje.getTiempoUso(), viaje.getTiempoUso() + totalPausaMinutos);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double calcularTiempoTotalConPausas(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Sumar tiempo de pausas completadas
        double totalPausaMinutos = viaje.getPausas().stream()
                .filter(pausa -> pausa.getFin() != null) // Ignorar pausas en curso
                .mapToDouble(pausa -> Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes())
                .sum();

        // Retornar tiempo total incluyendo pausas
        return viaje.getTiempoUso() + totalPausaMinutos;
    }
}

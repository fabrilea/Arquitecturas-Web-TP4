package edu.tudai.microservicioadministrador.service;

import edu.tudai.microservicioadministrador.client.MonopatinClient;
import edu.tudai.microservicioadministrador.entity.Mantenimiento;
import edu.tudai.microservicioadministrador.repository.MantenimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final MonopatinClient monopatinClient;

    @Transactional(readOnly = true)
    public List<Mantenimiento> findAll() {
        return mantenimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mantenimiento findById(Long id) {
        return mantenimientoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Mantenimiento save(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    @Transactional
    public void delete(Long id) {
        mantenimientoRepository.deleteById(id);
    }

    @Transactional
    public Mantenimiento update(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    /****************************************************/

    @Transactional
    public Mantenimiento iniciarMantenimiento(Long monopatinId, String descripcion) {
        // Verificar si el monopatín ya está en mantenimiento
        if (mantenimientoRepository.findByMonopatin(monopatinId)) {
            throw new RuntimeException("El monopatín ya está en mantenimiento");
        }

        // Crear el registro de mantenimiento
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setMonopatinId(monopatinId);
        mantenimiento.setFechaInicio(LocalDateTime.now());
        mantenimiento.setDescripcion(descripcion);
        mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como no disponible
        monopatinClient.actualizarDisponibilidad(monopatinId, false);

        return mantenimiento;
    }

    @Transactional
    public Mantenimiento finalizarMantenimiento(Long monopatinId) {

        Mantenimiento mantenimiento = mantenimientoRepository.findById(monopatinId).orElse(null);

        // Establecer la fecha de finalización
        mantenimiento.setFechaFin(LocalDateTime.now());
        mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como disponible
        monopatinClient.actualizarDisponibilidad(monopatinId, true);

        return mantenimiento;
    }
}
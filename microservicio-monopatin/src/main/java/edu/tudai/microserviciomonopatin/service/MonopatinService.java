package edu.tudai.microserviciomonopatin.service;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.repository.MonopatinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MonopatinService {
    private final MonopatinRepository monopatinRepository;

    @Transactional(readOnly = true)
    public List<Monopatin> findAll() {
        return monopatinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    @Transactional
    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public Monopatin update(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    /*******************************************************/
}
